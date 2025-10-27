package epicarchitect.mindreset.habits

import epicarchitect.mindreset.database.HabitEventRecord
import epicarchitect.mindreset.datetime.MonthOfYear
import epicarchitect.mindreset.datetime.duration
import epicarchitect.mindreset.datetime.monthOfYear
import epicarchitect.mindreset.datetime.monthOfYearRange
import epicarchitect.mindreset.datetime.mountsBetween
import epicarchitect.mindreset.math.ranges.mergedByOverlappingRanges
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

fun List<HabitEventRecord>.failedRanges() = map {
    it.startTime..it.endTime
}.mergedByOverlappingRanges()

fun habitAbstinenceRangesByFailedRanges(
    failedRanges: List<ClosedRange<Instant>>,
    currentTime: Instant
) = List(failedRanges.size) { index ->
    if (index == failedRanges.lastIndex) {
        failedRanges[index].endInclusive..currentTime
    } else {
        failedRanges[index].endInclusive..failedRanges[index + 1].start
    }
}

fun habitAbstinenceDurationSinceFirstTrack(
    failedRanges: List<ClosedRange<Instant>>,
    currentTime: Instant
) = failedRanges.minOfOrNull(ClosedRange<Instant>::start)?.let {
    (it..currentTime).duration()
}

fun List<HabitEventRecord>.countEventsInMonth(
    monthOfYear: MonthOfYear,
    timeZone: TimeZone
) = filterByMonth(monthOfYear, timeZone).countEvents()

fun List<HabitEventRecord>.countEvents() = fold(0) { total, track ->
    total + track.eventCount
}

fun List<HabitEventRecord>.filterByMonth(
    monthOfYear: MonthOfYear,
    timeZone: TimeZone
) = filter {
    monthOfYear in it.timeRange().monthOfYearRange(timeZone)
}

fun HabitEventRecord.timeRange() = startTime..endTime

fun List<HabitEventRecord>.groupByMonth(timeZone: TimeZone): Map<MonthOfYear, Set<HabitEventRecord>> {
    val map = mutableMapOf<MonthOfYear, MutableSet<HabitEventRecord>>()
    forEach { track ->
        val startMonth = track.startTime.monthOfYear(timeZone)
        val endMonth = track.endTime.monthOfYear(timeZone)
        val monthRange = startMonth..endMonth
        map.getOrPut(startMonth, ::mutableSetOf).add(track)
        map.getOrPut(endMonth, ::mutableSetOf).add(track)
        monthRange.mountsBetween().forEach {
            map.getOrPut(it, ::mutableSetOf).add(track)
        }
    }
    return map
}

fun HabitEventRecord.abstinence(currentTime: Instant) = (endTime..currentTime).duration()