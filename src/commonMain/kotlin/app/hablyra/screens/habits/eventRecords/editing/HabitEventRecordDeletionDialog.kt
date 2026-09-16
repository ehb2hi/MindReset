package app.hablyra.screens.habits.eventRecords.editing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.uikit.Dialog

@Composable
fun HabitEventRecordDeletionDialog(
    recordId: Int,
    onDismiss: () -> Unit,
    onDeleted: () -> Unit
) {
    val environment = LocalAppEnvironment.current
    val strings = environment.resources.strings.habitEventRecordEditingStrings
    val habitEventRecordQueries = environment.database.habitEventRecordQueries
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    Dialog(onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.space24),
            verticalArrangement = Arrangement.spacedBy(spacing.space16)
        ) {
            Text(
                text = strings.deleteConfirmation(),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = strings.deleteDescription(),
                color = colors.contentSecondary,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(spacing.space12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(text = strings.cancel())
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.error,
                        contentColor = colors.contentOnPrimary
                    ),
                    onClick = {
                        habitEventRecordQueries.deleteById(recordId)
                        onDeleted()
                    }
                ) {
                    Text(text = strings.confirmDeleteButton())
                }
            }
        }
    }
}
