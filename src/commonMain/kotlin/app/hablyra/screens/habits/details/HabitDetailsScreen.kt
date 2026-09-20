package app.hablyra.screens.habits.details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import app.hablyra.design.HablyraTheme
import app.hablyra.design.SegmentedControl
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute
import app.hablyra.uikit.SimpleScrollableScreen
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

private enum class DetailPeriod { ThirtyDays, ThreeMonths, All }

@Composable
fun HabitDetailsScreen(habitId: Int) {
    val state = rememberHabitDetailsScreenState(habitId) ?: return
    val environment = LocalAppEnvironment.current
    val density = LocalDensity.current
    val navController = LocalRootNavController.current
    val strings = environment.resources.strings
    val habitStrings = strings.habitDashboardStrings
    val spacing = HablyraTheme.spacing
    val scrollState = rememberScrollState()
    var period by rememberSaveable { mutableStateOf(DetailPeriod.ThirtyDays) }
    val showNameInAppBar by remember {
        derivedStateOf { with(density) { scrollState.value.toDp() > 80.dp } }
    }
    val today = environment.dateTime.currentInstant()
        .toLocalDateTime(environment.dateTime.currentTimeZone()).date
    val cutoff = when (period) {
        DetailPeriod.ThirtyDays -> today.minus(DatePeriod(days = 30))
        DetailPeriod.ThreeMonths -> today.minus(DatePeriod(months = 3))
        DetailPeriod.All -> null
    }
    val scopedState = remember(state, cutoff) {
        if (cutoff == null) state else state.copy(
            calendarRanges = state.calendarRanges.filter { it.endInclusive >= cutoff }
        )
    }

    SimpleScrollableScreen(
        title = if (showNameInAppBar) state.habit.name else "",
        scrollState = scrollState,
        onBackClick = navController::popBackStack,
        actions = {
            IconButton(onClick = { navController.navigate(RootRoute.HabitEditing(habitId)) }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = habitStrings.editHabitContentDescription()
                )
            }
        }
    ) {
        Spacer(Modifier.height(spacing.space20))
        HabitDetailsHeaderSection(
            modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
            state = state
        )
        if (state.habitEventRecords.isNotEmpty()) {
            Spacer(Modifier.height(spacing.space24))
            SegmentedControl(
                modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
                options = listOf(
                    DetailPeriod.ThirtyDays to strings.period30Days(),
                    DetailPeriod.ThreeMonths to strings.period3Months(),
                    DetailPeriod.All to strings.periodAll()
                ),
                selected = period,
                onSelected = { period = it }
            )
            Spacer(Modifier.height(spacing.space24))
            Text(
                modifier = Modifier.padding(horizontal = spacing.screenHorizontal),
                text = habitStrings.abstinenceChartTitle(),
                color = HablyraTheme.colors.contentPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            HabitDetailsCalendarCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
                state = scopedState
            )
        }
        if (state.statistics.isNotEmpty()) {
            Spacer(Modifier.height(spacing.space24))
            HabitDetailsStatisticsCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
                state = state
            )
        }
        Spacer(Modifier.height(spacing.space24))
    }
}
