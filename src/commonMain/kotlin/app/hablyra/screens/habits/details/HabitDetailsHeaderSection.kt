package app.hablyra.screens.habits.details

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.design.LoopMark
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.format.DurationFormatter
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute

@Composable
fun HabitDetailsHeaderSection(
    modifier: Modifier = Modifier,
    state: HabitDetailsScreenState
) {
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val durationFormatter = environment.format.durationFormatter
    val strings = environment.resources.strings
    val habitStrings = strings.habitDashboardStrings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val currentText = state.abstinence?.let {
        durationFormatter.format(it, DurationFormatter.Accuracy.SECONDS)
    } ?: habitStrings.habitHasNoEvents()
    val longestText = state.longestAbstinence?.let {
        durationFormatter.format(it, DurationFormatter.Accuracy.SECONDS)
    }
    val progress = if (
        state.abstinence != null &&
        state.longestAbstinence != null &&
        state.longestAbstinence > kotlin.time.Duration.ZERO
    ) {
        (state.abstinence.inWholeMilliseconds.toFloat() /
            state.longestAbstinence.inWholeMilliseconds.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.space20)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.space12)
        ) {
            Box(
                modifier = Modifier
                    .size(spacing.space48)
                    .clip(MaterialTheme.shapes.medium)
                    .background(colors.brandSecondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = environment.habits.icons.getById(state.habit.iconId).imageVector,
                    tint = colors.brandPrimary,
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = state.habit.name,
                color = colors.contentPrimary,
                style = MaterialTheme.typography.headlineMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.space8)
        ) {
            LoopMark(
                size = spacing.space40 + spacing.space40 + spacing.space32,
                progress = progress,
                contentDescription = strings.streakProgressDescription()
            )
            Text(
                text = habitStrings.currentStreakLabel(),
                color = colors.contentSecondary,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = currentText,
                color = colors.contentPrimary,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
            if (longestText != null) {
                Text(
                    text = strings.longestStreakLabel() + ": " + longestText,
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
        HablyraPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(
                    RootRoute.HabitEventRecordEditing(
                        habitEventRecordId = null,
                        habitId = state.habit.id
                    )
                )
            }
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Text(habitStrings.addHabitEventRecord())
        }
    }
}
