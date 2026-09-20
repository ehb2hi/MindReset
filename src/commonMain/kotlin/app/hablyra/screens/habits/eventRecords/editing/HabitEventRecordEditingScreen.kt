package app.hablyra.screens.habits.eventRecords.editing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.habits.checkHabitEventCount
import app.hablyra.habits.checkHabitEventRecordTimeRange
import app.hablyra.math.ranges.ascended
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.uikit.DateTimeRangeInputCard
import app.hablyra.uikit.SimpleScrollableScreen
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.hours

private enum class QuickTimeOption { Now, HourAgo, Yesterday, Pick }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HabitEventRecordEditingScreen(
    habitEventRecordId: Int?,
    habitId: Int
) {
    val state = rememberHabitEventRecordEditingState(
        eventRecordId = habitEventRecordId,
        habitId = habitId
    ) ?: return
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val habitRules = environment.habits.rules
    val strings = environment.resources.strings.habitEventRecordEditingStrings
    val appStrings = environment.resources.strings
    val queries = environment.database.habitEventRecordQueries
    val timeZone = environment.dateTime.currentTimeZone()
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    var showDeletion by remember { mutableStateOf(false) }
    var quickTime by remember {
        mutableStateOf(if (state.initialRecord == null) QuickTimeOption.Now else QuickTimeOption.Pick)
    }
    var showCustomTime by remember {
        mutableStateOf(state.initialRecord != null || state.dateTimeInputAsRange)
    }

    if (showDeletion) {
        HabitEventRecordDeletionDialog(
            recordId = state.initialRecord!!.id,
            onDismiss = { showDeletion = false },
            onDeleted = navController::popBackStack
        )
    }

    val saveRecord = save@{
        state.eventCountError = checkHabitEventCount(state.eventCount)
        if (state.eventCountError != null) return@save
        state.timeRangeError = checkHabitEventRecordTimeRange(
            timeRange = state.timeRange,
            currentTime = environment.dateTime.currentInstant()
        )
        if (state.timeRangeError != null) return@save
        if (state.initialRecord == null) {
            queries.insert(
                habitId = state.habit.id,
                startTime = state.timeRange.start,
                endTime = state.timeRange.endInclusive,
                eventCount = state.eventCount,
                comment = state.comment
            )
        } else {
            queries.update(
                id = state.initialRecord.id,
                startTime = state.timeRange.start,
                endTime = state.timeRange.endInclusive,
                eventCount = state.eventCount,
                comment = state.comment
            )
        }
        navController.popBackStack()
    }

    SimpleScrollableScreen(
        title = strings.titleText(
            isNewRecord = state.initialRecord == null,
            habitName = state.habit.name
        ),
        onBackClick = navController::popBackStack,
        navigationIcon = Icons.Filled.Close,
        navigationContentDescription = appStrings.closeButtonContentDescription(),
        footer = {
            androidx.compose.material3.Surface(
                color = colors.background,
                shadowElevation = 4.dp
            ) {
                HablyraPrimaryButton(
                    modifier = Modifier.fillMaxWidth().padding(
                        horizontal = spacing.screenHorizontal,
                        vertical = spacing.space12
                    ),
                    onClick = saveRecord
                ) {
                    Text(strings.finishButton())
                }
            }
        }
    ) {
        Spacer(Modifier.height(spacing.space20))
        HablyraCard(
            modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal)
        ) {
            Column(
                modifier = Modifier.padding(spacing.space16),
                verticalArrangement = Arrangement.spacedBy(spacing.space8)
            ) {
                Text(
                    text = strings.eventCountTitle(),
                    color = colors.contentPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = strings.eventCountDescription(),
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.space12)
                ) {
                    FilledTonalIconButton(
                        modifier = Modifier.size(52.dp),
                        onClick = { state.eventCount = (state.eventCount - 1).coerceAtLeast(1) },
                        enabled = state.eventCount > 1
                    ) {
                        Icon(
                            Icons.Filled.Remove,
                            contentDescription = strings.decreaseEventCountContentDescription()
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = state.eventCount.toString(),
                        color = colors.contentPrimary,
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center
                    )
                    FilledTonalIconButton(
                        modifier = Modifier.size(52.dp),
                        onClick = {
                            state.eventCount =
                                (state.eventCount + 1).coerceAtMost(habitRules.maxEventCount)
                        },
                        enabled = state.eventCount < habitRules.maxEventCount
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = strings.increaseEventCountContentDescription()
                        )
                    }
                }
                state.eventCountError?.let {
                    Text(
                        text = strings.eventCountError(it),
                        color = colors.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(Modifier.height(spacing.space24))
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
            verticalArrangement = Arrangement.spacedBy(spacing.space12)
        ) {
            Text(
                text = strings.timeRangeTitle(),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = environment.format.dateTimeFormatter.format(
                    state.timeRange.start.toLocalDateTime(timeZone)
                ),
                color = colors.contentSecondary,
                style = MaterialTheme.typography.bodyLarge
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(spacing.space8),
                verticalArrangement = Arrangement.spacedBy(spacing.space8)
            ) {
                QuickTimeChip(appStrings.quickNow(), quickTime == QuickTimeOption.Now) {
                    quickTime = QuickTimeOption.Now
                    val now = environment.dateTime.currentInstant()
                    state.timeRange = now..now
                    state.dateTimeInputAsRange = false
                    showCustomTime = false
                }
                QuickTimeChip(appStrings.quickHourAgo(), quickTime == QuickTimeOption.HourAgo) {
                    quickTime = QuickTimeOption.HourAgo
                    val time = environment.dateTime.currentInstant() - 1.hours
                    state.timeRange = time..time
                    state.dateTimeInputAsRange = false
                    showCustomTime = false
                }
                QuickTimeChip(appStrings.quickYesterday(), quickTime == QuickTimeOption.Yesterday) {
                    quickTime = QuickTimeOption.Yesterday
                    val time = environment.dateTime.currentInstant() - 24.hours
                    state.timeRange = time..time
                    state.dateTimeInputAsRange = false
                    showCustomTime = false
                }
                QuickTimeChip(appStrings.quickPick(), quickTime == QuickTimeOption.Pick) {
                    quickTime = QuickTimeOption.Pick
                    showCustomTime = true
                }
            }
        }

        if (showCustomTime || state.dateTimeInputAsRange) {
            Spacer(Modifier.height(spacing.space12))
            DateTimeRangeInputCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
                title = strings.timeRangeTitle(),
                description = strings.timeRangeDescription(),
                error = state.timeRangeError?.let(strings::timeRangeError),
                value = state.timeRange,
                onChanged = {
                    state.timeRange = it.ascended()
                    state.timeRangeError = null
                },
                startTimeLabel = strings.startDateTimeLabel(),
                endTimeLabel = strings.endDateTimeLabel(),
                timeZone = timeZone,
                showAsRangeMode = state.dateTimeInputAsRange,
                onRangeModeChanged = { state.dateTimeInputAsRange = it },
                showNowLabel = false
            )
        }

        Spacer(Modifier.height(spacing.space16))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
            value = state.comment,
            onValueChange = { state.comment = it },
            label = { Text(strings.commentTitle()) },
            supportingText = { Text(strings.commentDescription()) },
            minLines = 3,
            shape = MaterialTheme.shapes.small
        )

        if (state.initialRecord != null) {
            Spacer(Modifier.height(spacing.space20))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.screenHorizontal),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.error),
                onClick = { showDeletion = true }
            ) {
                Text(strings.deleteButton())
            }
        }

        Spacer(Modifier.height(spacing.space20))
    }
}

@Composable
private fun QuickTimeChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        modifier = Modifier.heightIn(min = 40.dp),
        selected = selected,
        onClick = onClick,
        label = { Text(label) }
    )
}
