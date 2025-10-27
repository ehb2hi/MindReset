package epicarchitect.mindreset.resources.strings.app

import epicarchitect.mindreset.resources.strings.appDashboard.AppDashboardStrings
import epicarchitect.mindreset.resources.strings.format.duration.DurationFormattingStrings
import epicarchitect.mindreset.resources.strings.habits.dashboard.HabitDashboardStrings
import epicarchitect.mindreset.resources.strings.habits.editing.HabitEditingStrings
import epicarchitect.mindreset.resources.strings.habits.eventRecords.dashboard.HabitEventRecordsDashboardStrings
import epicarchitect.mindreset.resources.strings.habits.eventRecords.editing.HabitEventRecordEditingStrings
import kotlinx.datetime.format.MonthNames

interface AppStrings {
    val appDashboardStrings: AppDashboardStrings
    val habitDashboardStrings: HabitDashboardStrings
    val habitEditingStrings: HabitEditingStrings
    val habitEventRecordsDashboardStrings: HabitEventRecordsDashboardStrings
    val habitEventRecordEditingStrings: HabitEventRecordEditingStrings
    val durationFormattingStrings: DurationFormattingStrings
    val monthNames: MonthNames
}

