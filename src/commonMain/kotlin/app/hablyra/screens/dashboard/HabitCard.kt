package app.hablyra.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import app.hablyra.database.Habit
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.format.DurationFormatter
import app.hablyra.habits.abstinence
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


@Composable
fun HabitCard(
    habit: Habit,
    modifier: Modifier = Modifier
) {
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val durationFormatter = environment.format.durationFormatter
    val eventRecordQueries = environment.database.habitEventRecordQueries
    val strings = environment.resources.strings.habitDashboardStrings
    val icons = environment.habits.icons
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val currentTime by environment.habits.timePulse.state.collectAsState()
    val lastRecordState = remember {
        eventRecordQueries.recordByHabitIdAndMaxEndTime(habit.id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
    }.collectAsState(null)
    val progressText = lastRecordState.value?.abstinence(currentTime)?.let {
        durationFormatter.format(it, DurationFormatter.Accuracy.SECONDS)
    } ?: strings.habitHasNoEvents()

    HablyraCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { navController.navigate(RootRoute.HabitDetails(habit.id)) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.space16),
            horizontalArrangement = Arrangement.spacedBy(spacing.space16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(spacing.space48)
                    .clip(MaterialTheme.shapes.medium)
                    .background(colors.brandSecondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icons.getById(habit.iconId).imageVector,
                    tint = colors.brandPrimary,
                    contentDescription = habit.name
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing.space4)
            ) {
                Text(
                    text = habit.name,
                    color = colors.contentPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Text(
                    text = strings.currentStreakLabel(),
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = progressText,
                    color = colors.contentPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            HablyraPrimaryButton(
                onClick = {
                    navController.navigate(
                        RootRoute.HabitEventRecordEditing(
                            habitEventRecordId = null,
                            habitId = habit.id
                        )
                    )
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                Text(text = strings.addHabitEventRecord())
            }
        }
    }
}
