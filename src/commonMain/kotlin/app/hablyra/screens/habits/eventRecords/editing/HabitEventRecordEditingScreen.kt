package app.hablyra.screens.habits.eventRecords.editing

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.habits.checkHabitEventCount
import app.hablyra.habits.checkHabitEventRecordTimeRange
import app.hablyra.math.ranges.ascended
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.uikit.DateTimeRangeInputCard
import app.hablyra.uikit.SimpleScrollableScreen
import app.hablyra.uikit.TextInputCard

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
    val habitEventRecordQueries = environment.database.habitEventRecordQueries
    val timeZone = environment.dateTime.currentTimeZone()
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    var showDeletion by remember { mutableStateOf(false) }

    if (showDeletion) {
        HabitEventRecordDeletionDialog(
            recordId = state.initialRecord!!.id,
            onDismiss = { showDeletion = false },
            onDeleted = navController::popBackStack
        )
    }
    SimpleScrollableScreen(
        title = strings.titleText(
            isNewRecord = state.initialRecord == null,
            habitName = state.habit.name
        ),
        onBackClick = navController::popBackStack
    ) {
        Spacer(Modifier.height(spacing.space20))

        TextInputCard(
            title = strings.eventCountTitle(),
            description = strings.eventCountDescription(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal),
            value = state.eventCount.let {
                if (it == 0) ""
                else it.toString()
            },
            onValueChange = {
                val value = it.toIntOrNull() ?: 0
                if (value <= habitRules.maxEventCount) {
                    state.eventCount = value
                    state.eventCountError = null
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            error = state.eventCountError?.let(strings::eventCountError)
        )

        Spacer(Modifier.height(spacing.space16))

        DateTimeRangeInputCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal),
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
            onRangeModeChanged = { state.dateTimeInputAsRange = it }
        )

        Spacer(Modifier.height(spacing.space16))

        TextInputCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal),
            title = strings.commentTitle(),
            description = strings.commentDescription(),
            value = state.comment,
            onValueChange = {
                state.comment = it
            },
            multiline = true
        )

        if (state.initialRecord != null) {
            Spacer(modifier = Modifier.height(spacing.space20))

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.screenHorizontal),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.error
                ),
                onClick = {
                    showDeletion = true
                }
            ) {
                Text(text = strings.deleteButton())
            }
        }

        Spacer(modifier = Modifier.weight(1.0f))
        Spacer(modifier = Modifier.height(spacing.space20))

        HablyraPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal),
            onClick = {
                state.eventCountError = checkHabitEventCount(state.eventCount)
                if (state.eventCountError != null) return@HablyraPrimaryButton

                state.timeRangeError = checkHabitEventRecordTimeRange(
                    timeRange = state.timeRange,
                    currentTime = environment.dateTime.currentInstant()
                )
                if (state.timeRangeError != null) return@HablyraPrimaryButton

                if (state.initialRecord == null) {
                    habitEventRecordQueries.insert(
                        habitId = state.habit.id,
                        startTime = state.timeRange.start,
                        endTime = state.timeRange.endInclusive,
                        eventCount = state.eventCount,
                        comment = state.comment
                    )
                } else {
                    habitEventRecordQueries.update(
                        id = state.initialRecord.id,
                        startTime = state.timeRange.start,
                        endTime = state.timeRange.endInclusive,
                        eventCount = state.eventCount,
                        comment = state.comment
                    )
                }

                navController.popBackStack()
            }
        ) {
            Text(text = strings.finishButton())
        }

        Spacer(modifier = Modifier.height(spacing.space20))
    }
}
