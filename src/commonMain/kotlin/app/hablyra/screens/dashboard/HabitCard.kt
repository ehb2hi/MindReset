package app.hablyra.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.hablyra.database.Habit
import app.hablyra.database.HabitEventRecord
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraTheme
import app.hablyra.design.RhythmStrip
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.format.DurationFormatter
import app.hablyra.habits.abstinence
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

@Composable
fun HabitCard(
    habit: Habit,
    records: List<HabitEventRecord>,
    modifier: Modifier = Modifier
) {
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val strings = environment.resources.strings
    val habitStrings = strings.habitDashboardStrings
    val currentTime = environment.habits.timePulse.state.collectAsState().value
    val timeZone = environment.dateTime.currentTimeZone()
    val habitRecords = remember(habit.id, records) { records.filter { it.habitId == habit.id } }
    val lastRecord = remember(habitRecords) { habitRecords.maxByOrNull { it.endTime } }
    val progressText = lastRecord?.abstinence(currentTime)?.let {
        environment.format.durationFormatter.format(it, DurationFormatter.Accuracy.SECONDS)
    } ?: habitStrings.habitHasNoEvents()
    val today = currentTime.toLocalDateTime(timeZone).date
    val rhythm = remember(habitRecords, today, timeZone) {
        List(14) { index ->
            val date = today.minus(DatePeriod(days = 13 - index))
            habitRecords.any {
                val start = it.startTime.toLocalDateTime(timeZone).date
                val end = it.endTime.toLocalDateTime(timeZone).date
                date in start..end
            }
        }
    }

    HablyraCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { navController.navigate(RootRoute.HabitDetails(habit.id)) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(spacing.space16),
            verticalArrangement = Arrangement.spacedBy(spacing.space16)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.space12)
            ) {
                Box(
                    modifier = Modifier
                        .size(spacing.space40)
                        .clip(MaterialTheme.shapes.medium)
                        .background(colors.brandSecondary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = environment.habits.icons.getById(habit.iconId).imageVector,
                        tint = colors.brandPrimary,
                        contentDescription = null
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = habit.name,
                    color = colors.contentPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(spacing.space4)) {
                Text(
                    text = habitStrings.currentStreakLabel(),
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = progressText,
                    color = colors.contentPrimary,
                    style = MaterialTheme.typography.displaySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            RhythmStrip(
                days = rhythm,
                contentDescription = strings.rhythmDescription(rhythm.count { it })
            )
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                onClick = {
                    navController.navigate(
                        RootRoute.HabitEventRecordEditing(
                            habitEventRecordId = null,
                            habitId = habit.id
                        )
                    )
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Text(habitStrings.addHabitEventRecord())
            }
        }
    }
}
