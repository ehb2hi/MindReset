package app.hablyra.uikit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Composable
fun DateTimeRangeInputCard(
    title: String,
    description: String,
    startTimeLabel: String,
    endTimeLabel: String,
    value: ClosedRange<Instant>,
    onChanged: (ClosedRange<Instant>) -> Unit,
    timeZone: TimeZone,
    error: String? = null,
    modifier: Modifier = Modifier,
    showAsRangeMode: Boolean,
    onRangeModeChanged: (Boolean) -> Unit
) {
    val environment = LocalAppEnvironment.current
    val strings = environment.resources.strings.habitEventRecordEditingStrings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val startDateTime = value.start.toLocalDateTime(timeZone)
    val currentDateTime = environment.dateTime.currentInstant().toLocalDateTime(timeZone)

    InputCard(
        modifier = modifier,
        title = title,
        description = description,
        error = error
    ) {
        Column {
            AnimatedVisibility(visible = showAsRangeMode) {
                Text(
                    modifier = Modifier.padding(bottom = spacing.space4),
                    text = startTimeLabel,
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            DateTimeInputRow(
                modifier = Modifier.fillMaxWidth(),
                value = value.start,
                onChanged = {
                    if (!showAsRangeMode) {
                        onChanged(it..it)
                    } else {
                        onChanged(it..value.endInclusive)
                    }
                },
                isSelectableDate = {
                    it <= currentDateTime.date
                },
                timeZone = timeZone
            )
        }

        AnimatedVisibility(visible = showAsRangeMode) {
            Column(
                modifier = Modifier.padding(top = spacing.space16)
            ) {
                Text(
                    text = endTimeLabel,
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.labelLarge
                )

                DateTimeInputRow(
                    modifier = Modifier
                        .padding(top = spacing.space4)
                        .fillMaxWidth(),
                    value = value.endInclusive,
                    onChanged = {
                        onChanged(value.start..it)
                    },
                    isSelectableDate = {
                        it >= startDateTime.date && it <= currentDateTime.date
                    },
                    timeZone = timeZone
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.space16))

        val onViewModeChanged = { checked: Boolean ->
            if (!checked) {
                onChanged(value.start..value.start)
            }

            onRangeModeChanged(checked)
        }

        Row(
            Modifier.clickable(
                indication = null,
                interactionSource = null
            ) {
                onViewModeChanged(!showAsRangeMode)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = showAsRangeMode,
                onCheckedChange = onViewModeChanged
            )

            Text(
                modifier = Modifier.padding(start = spacing.space4),
                text = strings.inputDateTimeAsRangeCheckbox(),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateTimeInputRow(
    value: Instant,
    isSelectableDate: (LocalDate) -> Boolean,
    timeZone: TimeZone,
    onChanged: (Instant) -> Unit,
    modifier: Modifier = Modifier
) {
    val environment = LocalAppEnvironment.current
    val strings = environment.resources.strings.habitEventRecordEditingStrings
    val dateTimeFormatter = environment.format.dateTimeFormatter
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val dateTime = value.toLocalDateTime(timeZone)
    var dateSelectionVisible by rememberSaveable { mutableStateOf(false) }
    var timeSelectionVisible by rememberSaveable { mutableStateOf(false) }

    if (dateSelectionVisible) {
        val date = dateTime.date
        val state = rememberDatePickerState(
            initialSelectedDateMillis = date.toEpochMillis(),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long) = isSelectableDate(
                    Instant.fromEpochMilliseconds(utcTimeMillis).toLocalDateTime(timeZone).date
                )
            }
        )

        DatePickerDialog(
            onDismissRequest = {
                dateSelectionVisible = false
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dateSelectionVisible = false
                    }
                ) {
                    Text(text = strings.cancel())
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newDate = Instant.fromEpochMilliseconds(state.selectedDateMillis!!)
                            .toLocalDateTime(timeZone).date
                        dateSelectionVisible = false
                        onChanged(LocalDateTime(newDate, dateTime.time).toInstant(timeZone))
                    }
                ) {
                    Text(text = strings.done())
                }
            }
        ) {
            DatePicker(state)
        }
    }

    if (timeSelectionVisible) {
        val time = dateTime.time
        val state = rememberTimePickerState(
            initialHour = time.hour,
            initialMinute = time.minute
        )
        Dialog(
            onDismiss = {
                timeSelectionVisible = false
            }
        ) {
            Column(
                modifier = Modifier.padding(spacing.space24),
                verticalArrangement = Arrangement.spacedBy(spacing.space16)
            ) {
                TimePicker(state)

                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(spacing.space12),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            timeSelectionVisible = false
                        }
                    ) {
                        Text(text = strings.cancel())
                    }

                    HablyraPrimaryButton(
                        onClick = {
                            val newTime = LocalTime(state.hour, state.minute, 0)
                            timeSelectionVisible = false
                            onChanged(LocalDateTime(dateTime.date, newTime).toInstant(timeZone))
                        }
                    ) {
                        Text(text = strings.done())
                    }
                }
            }
        }
    }

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = colors.borderSubtle,
                shape = MaterialTheme.shapes.small
            )
            .clip(MaterialTheme.shapes.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clickable {
                    dateSelectionVisible = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(spacing.space16))
            Icon(
                imageVector = Icons.Filled.CalendarToday,
                tint = colors.brandPrimary,
                contentDescription = null
            )
            Spacer(Modifier.width(spacing.space8))
            Text(
                text = dateTimeFormatter.format(dateTime.date),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.width(spacing.space16))
        }
        VerticalDivider(
            modifier = Modifier.height(56.dp),
            thickness = 1.dp,
            color = colors.divider
        )
        Row(
            modifier = Modifier
                .height(56.dp)
                .clickable {
                    timeSelectionVisible = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(spacing.space16))
            Icon(
                imageVector = Icons.Filled.AccessTime,
                tint = colors.brandPrimary,
                contentDescription = null
            )
            Spacer(Modifier.width(spacing.space8))
            Text(
                text = dateTimeFormatter.format(dateTime.time),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.width(spacing.space16))
        }
    }
}

private fun LocalDate.toEpochMillis() = LocalDateTime(
    date = this,
    time = LocalTime(
        hour = 0,
        minute = 0,
        second = 0
    )
).toInstant(offset = UtcOffset.ZERO).toEpochMilliseconds()
