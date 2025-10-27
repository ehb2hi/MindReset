package epicarchitect.mindreset.habits

import kotlinx.datetime.Instant

fun checkHabitEventRecordTimeRange(
    timeRange: ClosedRange<Instant>,
    currentTime: Instant
) = when {
    currentTime < timeRange.start || currentTime < timeRange.endInclusive -> {
        HabitEventRecordTimeRangeError.BiggestThenCurrentTime
    }

    else -> null
}

sealed interface HabitEventRecordTimeRangeError {
    data object BiggestThenCurrentTime : HabitEventRecordTimeRangeError
}