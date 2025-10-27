package epicarchitect.mindreset.resources.strings.app

import epicarchitect.mindreset.resources.strings.appDashboard.RussianAppDashboardStrings
import epicarchitect.mindreset.resources.strings.format.datetime.RUSSIAN_FULL
import epicarchitect.mindreset.resources.strings.format.duration.RussianDurationFormattingStrings
import epicarchitect.mindreset.resources.strings.habits.dashboard.RussianHabitDashboardStrings
import epicarchitect.mindreset.resources.strings.habits.editing.RussianHabitEditingStrings
import epicarchitect.mindreset.resources.strings.habits.eventRecords.dashboard.RussianHabitEventRecordsDashboardStrings
import epicarchitect.mindreset.resources.strings.habits.eventRecords.editing.RussianHabitEventRecordEditingStrings
import kotlinx.datetime.format.MonthNames

class RussianAppStrings : AppStrings {
    override val appDashboardStrings = RussianAppDashboardStrings()
    override val habitDashboardStrings = RussianHabitDashboardStrings()
    override val habitEditingStrings = RussianHabitEditingStrings()
    override val habitEventRecordsDashboardStrings = RussianHabitEventRecordsDashboardStrings()
    override val habitEventRecordEditingStrings = RussianHabitEventRecordEditingStrings()
    override val durationFormattingStrings = RussianDurationFormattingStrings()
    override val monthNames = MonthNames.RUSSIAN_FULL
}