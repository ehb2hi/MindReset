package app.hablyra.screens.insights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.hablyra.database.Habit
import app.hablyra.database.HabitEventRecord
import app.hablyra.datetime.duration
import app.hablyra.design.EmptyState
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraTheme
import app.hablyra.design.LoopMark
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.format.DurationFormatter
import app.hablyra.habits.failedRanges
import app.hablyra.habits.habitAbstinenceRangesByFailedRanges
import app.hablyra.uikit.Histogram
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.seconds

@Composable
fun InsightsScreen() {
    val environment = LocalAppEnvironment.current
    val strings = environment.resources.strings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val currentTime = environment.habits.timePulse.state.collectAsState().value
    val records = remember {
        environment.database.habitEventRecordQueries.records().asFlow().mapToList(Dispatchers.IO)
    }.collectAsState(null).value ?: return
    val habits = remember {
        environment.database.habitQueries.habits().asFlow().mapToList(Dispatchers.IO)
    }.collectAsState(null).value ?: return
    val recordsByHabit = remember(records) { records.groupBy { it.habitId } }
    val habitsWithHistory = remember(habits, recordsByHabit) {
        habits.filter { recordsByHabit[it.id].orEmpty().isNotEmpty() }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(
                start = spacing.screenHorizontal,
                end = spacing.screenHorizontal,
                top = spacing.space24,
                bottom = spacing.space16
            ),
            text = strings.insightsTitle(),
            color = colors.contentPrimary,
            style = MaterialTheme.typography.headlineLarge
        )
        if (habitsWithHistory.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(horizontal = spacing.screenHorizontal),
                contentAlignment = Alignment.Center
            ) {
                EmptyState(
                    visual = { LoopMark() },
                    title = strings.insightsEmptyTitle(),
                    description = strings.insightsEmptyBody()
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = spacing.screenHorizontal,
                    end = spacing.screenHorizontal,
                    bottom = spacing.space40
                ),
                verticalArrangement = Arrangement.spacedBy(spacing.space16)
            ) {
                items(habitsWithHistory, key = Habit::id) { habit ->
                    InsightSection(
                        habit = habit,
                        records = recordsByHabit[habit.id].orEmpty(),
                        currentTime = currentTime
                    )
                }
            }
        }
    }
}

@Composable
private fun InsightSection(
    habit: Habit,
    records: List<HabitEventRecord>,
    currentTime: Instant
) {
    val environment = LocalAppEnvironment.current
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val strings = environment.resources.strings.habitDashboardStrings
    val ranges = remember(records, currentTime) {
        habitAbstinenceRangesByFailedRanges(records.failedRanges(), currentTime)
    }
    val values = remember(ranges) { ranges.map { it.duration().inWholeSeconds.toFloat() } }
    HablyraCard {
        Column(
            modifier = Modifier.padding(spacing.space16),
            verticalArrangement = Arrangement.spacedBy(spacing.space12)
        ) {
            Text(
                text = habit.name,
                color = colors.contentPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = strings.statisticsTitle(),
                color = colors.contentSecondary,
                style = MaterialTheme.typography.labelLarge
            )
            Histogram(
                modifier = Modifier.fillMaxWidth().height(spacing.space40 * 4),
                values = values,
                barColor = colors.chartPrimary,
                valueTextColor = colors.contentSecondary,
                valueFormatter = {
                    environment.format.durationFormatter.format(
                        it.toLong().seconds,
                        DurationFormatter.Accuracy.DAYS
                    )
                }
            )
        }
    }
}
