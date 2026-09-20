package app.hablyra.screens.habits.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import epicarchitect.calendar.compose.basis.contains
import epicarchitect.calendar.compose.basis.state.LocalBasisEpicCalendarState
import epicarchitect.calendar.compose.pager.EpicCalendarPager
import epicarchitect.calendar.compose.pager.state.rememberEpicCalendarPagerState
import epicarchitect.calendar.compose.ranges.drawEpicRanges
import app.hablyra.datetime.toMonthOfYear
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute

@Composable
fun HabitDetailsCalendarCard(
    modifier: Modifier = Modifier,
    state: HabitDetailsScreenState
) {
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val monthFormatter = environment.format.monthFormatter
    val strings = environment.resources.strings.habitDashboardStrings
    val calendarState = rememberEpicCalendarPagerState()
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = spacing.space16)
        ) {
            Text(
                modifier = Modifier,
                text = monthFormatter.format(calendarState.currentMonth.toMonthOfYear()),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.titleMedium
            )

            EpicCalendarPager(
                pageModifier = {
                    Modifier.drawEpicRanges(state.calendarRanges, colors.calendarActive)
                },
                dayOfMonthContent = { date ->
                    val basisState = LocalBasisEpicCalendarState.current!!
                    val isSelected = state.calendarRanges.any { date in it }
                    Text(
                        modifier = Modifier.alpha(
                            if (date in basisState.currentMonth) 1.0f
                            else 0.5f
                        ),
                        text = date.dayOfMonth.toString(),
                        textAlign = TextAlign.Center,
                        color = if (isSelected) colors.contentOnPrimary else colors.contentPrimary
                    )
                },
                state = calendarState
            )

            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.space8),
                onClick = {
                    navController.navigate(
                        RootRoute.HabitEventRecordsDetails(
                            habitId = state.habit.id
                        )
                    )
                }
            ) {
                Text(text = strings.showAllEventRecords())
            }
        }
    }
}
