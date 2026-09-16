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
import androidx.compose.ui.text.style.TextOverflow
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.design.ProgressMetric
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
    val strings = environment.resources.strings.habitDashboardStrings
    val habitIcons = environment.habits.icons
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    val progressText = state.abstinence?.let {
        durationFormatter.format(
            duration = it,
            accuracy = DurationFormatter.Accuracy.SECONDS
        )
    } ?: strings.habitHasNoEvents()

    HablyraCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(spacing.space20),
            verticalArrangement = Arrangement.spacedBy(spacing.space20)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.space12)
            ) {
                Box(
                    modifier = Modifier
                        .size(spacing.space40)
                        .clip(MaterialTheme.shapes.small)
                        .background(colors.surfaceSubtle),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = habitIcons.getById(state.habit.iconId).imageVector,
                        tint = colors.brandPrimary,
                        contentDescription = null
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(spacing.space4)
                ) {
                    Text(
                        text = strings.habitLabel(),
                        color = colors.contentSecondary,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = state.habit.name,
                        color = colors.contentPrimary,
                        style = MaterialTheme.typography.headlineLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
            }

            ProgressMetric(
                modifier = Modifier.fillMaxWidth(),
                label = strings.currentStreakLabel(),
                value = progressText
            )

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
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
                Text(text = strings.addHabitEventRecord())
            }
        }
    }
}
