package app.hablyra.resources.strings.app

import app.hablyra.resources.strings.appDashboard.RussianAppDashboardStrings
import app.hablyra.resources.strings.format.datetime.RUSSIAN_FULL
import app.hablyra.resources.strings.format.duration.RussianDurationFormattingStrings
import app.hablyra.resources.strings.habits.dashboard.RussianHabitDashboardStrings
import app.hablyra.resources.strings.habits.editing.RussianHabitEditingStrings
import app.hablyra.resources.strings.habits.eventRecords.dashboard.RussianHabitEventRecordsDashboardStrings
import app.hablyra.resources.strings.habits.eventRecords.editing.RussianHabitEventRecordEditingStrings
import kotlinx.datetime.format.MonthNames

class RussianAppStrings : AppStrings {
    override val appDashboardStrings = RussianAppDashboardStrings()
    override val habitDashboardStrings = RussianHabitDashboardStrings()
    override val habitEditingStrings = RussianHabitEditingStrings()
    override val habitEventRecordsDashboardStrings = RussianHabitEventRecordsDashboardStrings()
    override val habitEventRecordEditingStrings = RussianHabitEventRecordEditingStrings()
    override val durationFormattingStrings = RussianDurationFormattingStrings()
    override val monthNames = MonthNames.RUSSIAN_FULL
    override fun backButtonContentDescription() = "Назад"
}