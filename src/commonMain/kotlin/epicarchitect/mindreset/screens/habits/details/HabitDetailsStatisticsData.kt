package epicarchitect.mindreset.screens.habits.details

import epicarchitect.mindreset.database.HabitEventRecord
import epicarchitect.mindreset.datetime.averageDuration
import epicarchitect.mindreset.datetime.maxDuration
import epicarchitect.mindreset.datetime.minDuration
import epicarchitect.mindreset.datetime.monthOfYear
import epicarchitect.mindreset.datetime.orZero
import epicarchitect.mindreset.datetime.previous
import epicarchitect.mindreset.environment.AppEnvironment
import epicarchitect.mindreset.format.DurationFormatter
import epicarchitect.mindreset.habits.countEvents
import epicarchitect.mindreset.habits.countEventsInMonth
import epicarchitect.mindreset.habits.habitAbstinenceDurationSinceFirstTrack
import epicarchitect.mindreset.uikit.StatisticData
import kotlinx.datetime.Instant

fun habitDetailsStatisticsData(
    environment: AppEnvironment,
    habitEventRecords: List<HabitEventRecord>,
    abstinenceRanges: List<ClosedRange<Instant>>,
    failedRanges: List<ClosedRange<Instant>>,
    currentTime: Instant
): List<StatisticData> {
    val strings = environment.resources.strings.habitDashboardStrings
    val durationFormatter = environment.format.durationFormatter
    val numberFormatter = environment.format.numberFormatter
    val timeZone = environment.dateTime.currentTimeZone()

    return if (habitEventRecords.isNotEmpty()) {
        listOf(
            StatisticData(
                name = strings.statisticsAverageAbstinenceTime(),
                value = durationFormatter.format(
                    duration = abstinenceRanges.averageDuration().orZero(),
                    accuracy = DurationFormatter.Accuracy.HOURS
                )
            ),
            StatisticData(
                name = strings.statisticsMaxAbstinenceTime(),
                value = durationFormatter.format(
                    duration = abstinenceRanges.maxDuration().orZero(),
                    accuracy = DurationFormatter.Accuracy.HOURS
                )
            ),
            StatisticData(
                name = strings.statisticsMinAbstinenceTime(),
                value = durationFormatter.format(
                    duration = abstinenceRanges.minDuration().orZero(),
                    accuracy = DurationFormatter.Accuracy.HOURS
                )
            ),
            StatisticData(
                name = strings.statisticsDurationSinceFirstTrack(),
                value = durationFormatter.format(
                    duration = habitAbstinenceDurationSinceFirstTrack(
                        failedRanges = failedRanges,
                        currentTime = currentTime
                    ).orZero(),
                    accuracy = DurationFormatter.Accuracy.HOURS
                )
            ),
            StatisticData(
                name = strings.statisticsCountEventsInCurrentMonth(),
                value = numberFormatter.format(
                    habitEventRecords.countEventsInMonth(
                        monthOfYear = currentTime.monthOfYear(timeZone),
                        timeZone = timeZone
                    )
                )
            ),
            StatisticData(
                name = strings.statisticsCountEventsInPreviousMonth(),
                value = numberFormatter.format(
                    habitEventRecords.countEventsInMonth(
                        monthOfYear = currentTime.monthOfYear(timeZone).previous(),
                        timeZone = timeZone
                    )
                )
            ),
            StatisticData(
                name = strings.statisticsTotalCountEvents(),
                value = numberFormatter.format(
                    habitEventRecords.countEvents()
                )
            )
        )
    } else {
        emptyList()
    }
}