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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute
import app.hablyra.uikit.SimpleScrollableScreen

@Composable
fun HabitDetailsScreen(habitId: Int) {
    val state = rememberHabitDetailsScreenState(habitId) ?: return
    val density = LocalDensity.current
    val navController = LocalRootNavController.current
    val strings = LocalAppEnvironment.current.resources.strings.habitDashboardStrings

    val scrollState = rememberScrollState()
    val showNameInAppBar by remember {
        derivedStateOf {
            with(density) {
                scrollState.value.toDp() > 80.dp
            }
        }
    }

    SimpleScrollableScreen(
        title = if (showNameInAppBar) state.habit.name else "",
        scrollState = scrollState,
        onBackClick = navController::popBackStack,
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(RootRoute.HabitEditing(habitId))
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = strings.editHabitContentDescription()
                )
            }
        }
    ) {
        Spacer(Modifier.height(HablyraTheme.spacing.space20))

        HabitDetailsHeaderSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HablyraTheme.spacing.screenHorizontal),
            state = state
        )

        if (state.habitEventRecords.isNotEmpty()) {
            Spacer(Modifier.height(HablyraTheme.spacing.space20))
            HabitDetailsCalendarCard(
                modifier = Modifier
                    .padding(horizontal = HablyraTheme.spacing.screenHorizontal)
                    .fillMaxWidth(),
                state = state
            )
        }

        if (state.abstinenceHistogramValues.size > 2) {
            Spacer(Modifier.height(HablyraTheme.spacing.space20))
            HabitDetailsHistogramCard(
                modifier = Modifier
                    .padding(horizontal = HablyraTheme.spacing.screenHorizontal)
                    .fillMaxWidth(),
                state = state
            )
        }

        if (state.statistics.isNotEmpty()) {
            Spacer(Modifier.height(HablyraTheme.spacing.space20))
            HabitDetailsStatisticsCard(
                modifier = Modifier
                    .padding(horizontal = HablyraTheme.spacing.screenHorizontal)
                    .fillMaxWidth(),
                state = state
            )
        }

        Spacer(Modifier.height(HablyraTheme.spacing.space20))
    }
}