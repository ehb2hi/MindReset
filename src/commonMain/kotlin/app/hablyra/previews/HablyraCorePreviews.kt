package app.hablyra.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import app.hablyra.design.EmptyState
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.LoopMark
import app.hablyra.design.HablyraTheme
import app.hablyra.design.ProgressMetric
import app.hablyra.screens.habits.eventRecords.details.EventRecordItemContent
import app.hablyra.uikit.TextInputCard

@Preview
@Composable
fun HablyraCoreLightPreview() {
    HablyraCorePreviewContent(darkTheme = false)
}

@Preview
@Composable
fun HablyraCoreDarkPreview() {
    HablyraCorePreviewContent(darkTheme = true)
}

@Composable
private fun HablyraCorePreviewContent(darkTheme: Boolean) {
    HablyraTheme(darkTheme = darkTheme) {
        Surface(color = HablyraTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(HablyraTheme.spacing.space20),
                verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space16)
            ) {
                ProgressMetric(
                    label = "Current streak",
                    value = "18h 42m"
                )

                EventRecordItemContent(
                    dateTimeText = "September 13, 2026, 18:30 - 18:45",
                    eventCountText = "Occurrences: 2",
                    comment = "Longer reflection text is clipped after two lines so the history list remains easy to scan while preserving the full comment in editing.",
                    editContentDescription = "Edit event",
                    onClick = {}
                )

                EmptyState(
                    title = "No events yet",
                    description = "Events you log will appear here so you can understand your pattern over time.",
                    action = {
                        HablyraPrimaryButton(onClick = {}) {
                            Text(text = "Log event")
                        }
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun HablyraDashboardPreview() {
    HablyraTheme {
        Surface(color = HablyraTheme.colors.background) {
            Column(
                modifier = Modifier.padding(HablyraTheme.spacing.space20),
                verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space16)
            ) {
                Text(text = "Hablyra", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)
                Text(text = "See your progress at a glance.", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                PreviewHabitCard(name = "Evening scrolling", progress = "3d 8h")
                PreviewHabitCard(name = "Late-night snacking", progress = "No events yet")
            }
        }
    }
}

@Preview
@Composable
fun HablyraEmptyDashboardPreview() {
    HablyraTheme(darkTheme = true) {
        Surface(color = HablyraTheme.colors.background) {
            EmptyState(
                modifier = Modifier.padding(HablyraTheme.spacing.space20),
                visual = { LoopMark() },
                title = "Start with one habit",
                description = "Track a habit privately and make your progress easier to understand.",
                action = {
                    HablyraPrimaryButton(onClick = {}) { Text(text = "Add habit") }
                }
            )
        }
    }
}

@Preview
@Composable
fun HablyraAddHabitPreview() {
    HablyraTheme {
        Surface(color = HablyraTheme.colors.background) {
            Column(
                modifier = Modifier.padding(HablyraTheme.spacing.space20),
                verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space16)
            ) {
                Text(text = "Add habit", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
                TextInputCard(
                    title = "Habit name",
                    description = "Use a short name you will recognize quickly.",
                    value = "Evening scrolling",
                    onValueChange = {}
                )
                Text(text = "Choose an icon", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                LoopMark(size = HablyraTheme.spacing.space40)
                HablyraPrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) { Text(text = "Save habit") }
            }
        }
    }
}

@Preview
@Composable
fun HablyraHabitDetailsPreview() {
    HablyraTheme {
        Surface(color = HablyraTheme.colors.background) {
            Column(
                modifier = Modifier.padding(HablyraTheme.spacing.space20),
                verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space20)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space12),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    LoopMark(size = HablyraTheme.spacing.space40)
                    Text(text = "Evening scrolling", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
                }
                ProgressMetric(label = "Current streak", value = "3d 8h")
                HablyraPrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) { Text(text = "Log event") }
                Text(text = "History", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Text(text = "Insights", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Preview
@Composable
fun HablyraLogEventPreview() {
    HablyraTheme {
        Surface(color = HablyraTheme.colors.background) {
            LogEventPreviewContent(rangeExpanded = false)
        }
    }
}

@Preview
@Composable
fun HablyraLogEventRangePreview() {
    HablyraTheme(darkTheme = true) {
        Surface(color = HablyraTheme.colors.background) {
            LogEventPreviewContent(rangeExpanded = true)
        }
    }
}

@Composable
private fun PreviewHabitCard(name: String, progress: String) {
    HablyraCard {
        Row(
            modifier = Modifier.padding(HablyraTheme.spacing.space16),
            horizontalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space12),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            LoopMark(size = HablyraTheme.spacing.space40)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                Text(text = "Current streak", style = androidx.compose.material3.MaterialTheme.typography.labelMedium)
                Text(text = progress, style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
            }
            HablyraPrimaryButton(onClick = {}) { Text(text = "Log") }
        }
    }
}

@Composable
private fun LogEventPreviewContent(rangeExpanded: Boolean) {
    Column(
        modifier = Modifier.padding(HablyraTheme.spacing.space20),
        verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space16)
    ) {
        Text(text = "Log event", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Text(text = "Occurrences", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(text = "-", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
            Text(text = "1", style = androidx.compose.material3.MaterialTheme.typography.displaySmall)
            Text(text = "+", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        }
        Text(text = if (rangeExpanded) "Start 16 Sep 2026 · 17:30" else "Now 16 Sep 2026 · 17:52")
        Text(text = if (rangeExpanded) "End 16 Sep 2026 · 17:45" else "Add duration / specify time range")
        Text(text = "Comment (optional)", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
        HablyraPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) { Text(text = "Save event") }
    }
}


@Preview
@Composable
fun HablyraHistoryPreview() {
    HablyraTheme {
        Surface(color = HablyraTheme.colors.background) {
            Column(
                modifier = Modifier.padding(HablyraTheme.spacing.space20),
                verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space12)
            ) {
                Text("History", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)
                Text("Evening scrolling", color = HablyraTheme.colors.brandPrimary)
                EventRecordItemContent(
                    dateTimeText = "September 16, 2026, 17:52",
                    eventCountText = "Occurrences: 1",
                    comment = "A short note about the context.",
                    editContentDescription = "Edit event",
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun HablyraInsightsDarkPreview() {
    HablyraTheme(darkTheme = true) {
        Surface(color = HablyraTheme.colors.background) {
            Column(
                modifier = Modifier.padding(HablyraTheme.spacing.space20),
                verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space16)
            ) {
                Text("Insights", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)
                PreviewHabitCard(name = "Evening scrolling", progress = "3d 8h")
            }
        }
    }
}

@Preview
@Composable
fun HablyraSettingsPreview() {
    HablyraTheme {
        Surface(color = HablyraTheme.colors.background) {
            Column(
                modifier = Modifier.padding(HablyraTheme.spacing.space20),
                verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space16)
            ) {
                Text("Settings", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)
                Text("Appearance", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                Text("System default", color = HablyraTheme.colors.contentSecondary)
                Text("About Hablyra", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview
@Composable
fun HablyraLargeTextDashboardPreview() {
    val density = androidx.compose.ui.platform.LocalDensity.current
    androidx.compose.runtime.CompositionLocalProvider(
        androidx.compose.ui.platform.LocalDensity provides androidx.compose.ui.unit.Density(
            density = density.density,
            fontScale = 2f
        )
    ) {
        HablyraTheme {
            Surface(color = HablyraTheme.colors.background) {
                Column(
                    modifier = Modifier.padding(HablyraTheme.spacing.space20),
                    verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space16)
                ) {
                    Text(
                        "Hablyra",
                        style = androidx.compose.material3.MaterialTheme.typography.headlineLarge
                    )
                    PreviewHabitCard(
                        name = "A deliberately long habit name that wraps safely",
                        progress = "12 days"
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun HablyraEditHabitPreview() {
    HablyraTheme(darkTheme = true) {
        Surface(color = HablyraTheme.colors.background) {
            Column(
                modifier = Modifier.padding(HablyraTheme.spacing.space20),
                verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space16)
            ) {
                Text("Edit habit", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
                Text("Habit name", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                Text("Evening scrolling", style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
                Text("Choose an icon", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                LoopMark(size = HablyraTheme.spacing.space48)
                HablyraPrimaryButton(modifier = Modifier.fillMaxWidth(), onClick = {}) {
                    Text("Save changes")
                }
            }
        }
    }
}
