package app.hablyra.screens.habits.eventRecords.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import app.hablyra.database.HabitEventRecord
import app.hablyra.datetime.toLocalDateTimeRange
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.habits.timeRange
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute

@Composable
fun LazyItemScope.HabitRecordItem(
    item: HabitEventRecord,
    habitName: String? = null
) {
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val dateTimeFormatter = environment.format.dateTimeFormatter
    val strings = environment.resources.strings.habitEventRecordsDashboardStrings
    val timeZone = environment.dateTime.currentTimeZone()

    EventRecordItemContent(
        modifier = Modifier
            .animateItem()
            .fillMaxWidth(),
        dateTimeText = dateTimeFormatter.format(item.timeRange().toLocalDateTimeRange(timeZone)).toString(),
        eventCountText = strings.eventCount(item.eventCount),
        comment = item.comment,
        editContentDescription = strings.editEventContentDescription(),
        habitName = habitName,
        onClick = {
            navController.navigate(
                RootRoute.HabitEventRecordEditing(
                    habitEventRecordId = item.id,
                    habitId = item.habitId
                )
            )
        }
    )
}

@Composable
fun EventRecordItemContent(
    dateTimeText: String,
    eventCountText: String,
    comment: String,
    editContentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    habitName: String? = null
) {
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    HablyraCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.space16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.space12)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing.space4)
            ) {
                if (habitName != null) {
                    Text(
                        text = habitName,
                        color = colors.brandPrimary,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = dateTimeText,
                    color = colors.contentPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = eventCountText,
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )

                if (comment.isNotBlank()) {
                    Text(
                        text = comment,
                        color = colors.contentSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                tint = colors.contentTertiary,
                contentDescription = editContentDescription
            )
        }
    }
}
