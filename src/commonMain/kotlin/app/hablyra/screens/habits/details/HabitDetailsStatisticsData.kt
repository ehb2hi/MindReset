package app.hablyra.screens.habits.details

import app.hablyra.database.HabitEventRecord
import app.hablyra.datetime.averageDuration
import app.hablyra.datetime.maxDuration
import app.hablyra.datetime.minDuration
import app.hablyra.datetime.monthOfYear
import app.hablyra.datetime.orZero
import app.hablyra.datetime.previous
import app.hablyra.environment.AppEnvironment
import app.hablyra.format.DurationFormatter
import app.hablyra.habits.countEvents
import app.hablyra.habits.countEventsInMonth
import app.hablyra.habits.habitAbstinenceDurationSinceFirstTrack
import app.hablyra.uikit.StatisticData
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