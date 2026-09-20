package app.hablyra.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.hablyra.design.EmptyState
import app.hablyra.design.HablyraTheme
import app.hablyra.design.LoopMark
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.monetization.DashboardAdBanner
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Composable
fun DashboardScreen() {
    val state = rememberDashboardScreenState() ?: return
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val dashboardStrings = environment.resources.strings.appDashboardStrings
    val strings = environment.resources.strings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val currentTime = environment.habits.timePulse.state.collectAsState().value
    val timeZone = environment.dateTime.currentTimeZone()
    val today = currentTime.toLocalDateTime(timeZone).date
    val weekStartDate = today.minus(DatePeriod(days = today.dayOfWeek.ordinal))
    val weekStart = LocalDateTime(weekStartDate, LocalTime(0, 0)).toInstant(timeZone)
    val weekOccurrences = state.records
        .filter { it.endTime >= weekStart && it.startTime <= currentTime }
        .sumOf { it.eventCount }
    val onAddHabitClick = {
        navController.navigate(RootRoute.HabitEditing(habitId = null))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(top = spacing.space24)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
                verticalArrangement = Arrangement.spacedBy(spacing.space4)
            ) {
                Text(
                    text = dashboardStrings.titleText(),
                    color = colors.contentPrimary,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = dashboardStrings.subtitleText(),
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (state.habits.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(horizontal = spacing.screenHorizontal),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyState(
                        visual = { LoopMark() },
                        title = dashboardStrings.emptyHabitsTitle(),
                        description = dashboardStrings.emptyHabitsText()
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = spacing.screenHorizontal,
                        end = spacing.screenHorizontal,
                        top = spacing.space24,
                        bottom = spacing.space40 * 3
                    ),
                    verticalArrangement = Arrangement.spacedBy(spacing.space16)
                ) {
                    item {
                        Surface(
                            color = colors.brandPrimary,
                            contentColor = colors.contentOnPrimary,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(spacing.space16),
                                verticalArrangement = Arrangement.spacedBy(spacing.space4)
                            ) {
                                Text(
                                    text = strings.weekSummaryTitle(),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = strings.weekSummaryEvents(weekOccurrences),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                    items(state.habits, key = { it.id }) { habit ->
                        HabitCard(habit = habit, records = state.records)
                    }
                    item {
                        DashboardAdBanner(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(spacing.space20),
            onClick = onAddHabitClick,
            containerColor = colors.brandPrimary,
            contentColor = colors.contentOnPrimary
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = dashboardStrings.newHabitButtonText()
            )
        }
    }
}
