package app.hablyra.resources.strings.app

import app.hablyra.resources.strings.appDashboard.AppDashboardStrings
import app.hablyra.resources.strings.format.duration.DurationFormattingStrings
import app.hablyra.resources.strings.habits.dashboard.HabitDashboardStrings
import app.hablyra.resources.strings.habits.editing.HabitEditingStrings
import app.hablyra.resources.strings.habits.eventRecords.dashboard.HabitEventRecordsDashboardStrings
import app.hablyra.resources.strings.habits.eventRecords.editing.HabitEventRecordEditingStrings
import kotlinx.datetime.format.MonthNames

interface AppStrings {
    val appDashboardStrings: AppDashboardStrings
    val habitDashboardStrings: HabitDashboardStrings
    val habitEditingStrings: HabitEditingStrings
    val habitEventRecordsDashboardStrings: HabitEventRecordsDashboardStrings
    val habitEventRecordEditingStrings: HabitEventRecordEditingStrings
    val durationFormattingStrings: DurationFormattingStrings
    val monthNames: MonthNames
    fun backButtonContentDescription(): String
}

