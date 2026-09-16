package app.hablyra.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import app.hablyra.design.EmptyState
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.design.ProgressMetric
import app.hablyra.screens.habits.eventRecords.details.EventRecordItemContent

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
