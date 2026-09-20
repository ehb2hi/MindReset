package app.hablyra.screens.habits.eventRecords.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import epicarchitect.calendar.compose.basis.contains
import epicarchitect.calendar.compose.basis.state.LocalBasisEpicCalendarState
import epicarchitect.calendar.compose.pager.EpicCalendarPager
import epicarchitect.calendar.compose.ranges.drawEpicRanges
import app.hablyra.datetime.toMonthOfYear
import app.hablyra.design.EmptyState
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute
import app.hablyra.uikit.SimpleTopAppBar
import kotlinx.coroutines.launch

@Composable
fun HabitEventRecordsDetailsScreen(habitId: Int) {
    val state = rememberHabitEventRecordsDetailsScreenState(habitId) ?: return
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val monthFormatter = environment.format.monthFormatter
    val strings = environment.resources.strings.habitEventRecordsDashboardStrings
    val appStrings = environment.resources.strings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val onLogEventClick = {
        navController.navigate(
            RootRoute.HabitEventRecordEditing(
                habitEventRecordId = null,
                habitId = habitId
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SimpleTopAppBar(
            title = "",
            onBackClick = navController::popBackStack
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal)
                .padding(top = spacing.space20),
            verticalArrangement = Arrangement.spacedBy(spacing.space16)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.space4)
            ) {
                Text(
                    text = appStrings.habitDashboardStrings.abstinenceChartTitle(),
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = state.habit.name,
                    color = colors.contentPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            HablyraCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = spacing.space16),
                    verticalArrangement = Arrangement.spacedBy(spacing.space12)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.space8),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    state.calendarState.scrollMonths(-1)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = strings.previousMonthContentDescription()
                            )
                        }

                        Text(
                            modifier = Modifier.defaultMinSize(minWidth = 120.dp),
                            text = monthFormatter.format(state.calendarState.currentMonth.toMonthOfYear()),
                            textAlign = TextAlign.Center,
                            color = colors.contentPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )

                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    state.calendarState.scrollMonths(1)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = strings.nextMonthContentDescription()
                            )
                        }
                    }

                    EpicCalendarPager(
                        pageModifier = {
                            Modifier.drawEpicRanges(state.calendarRanges, colors.brandPrimary)
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
                        state = state.calendarState
                    )
                }
            }
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            if (state.currentMonthRecords.isEmpty()) {
                EmptyState(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = spacing.screenHorizontal),
                    title = if (state.records.isEmpty()) strings.emptyHistoryTitle() else strings.emptyMonthTitle(),
                    description = if (state.records.isEmpty()) strings.emptyHistoryBody() else strings.emptyMonthBody(),
                    action = {
                        HablyraPrimaryButton(onClick = onLogEventClick) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                            Text(text = strings.newTrackButton())
                        }
                    }
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = spacing.screenHorizontal,
                        end = spacing.screenHorizontal,
                        top = spacing.space20,
                        bottom = spacing.space40 + spacing.space40
                    ),
                    verticalArrangement = Arrangement.spacedBy(spacing.space12)
                ) {
                    items(
                        items = state.currentMonthRecords,
                        key = { it.id }
                    ) {
                        HabitRecordItem(it)
                    }
                }

                HablyraPrimaryButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(spacing.space20),
                    onClick = onLogEventClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                    Text(text = strings.newTrackButton())
                }
            }
        }
    }
}
