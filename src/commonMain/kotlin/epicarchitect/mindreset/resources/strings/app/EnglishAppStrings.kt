package epicarchitect.mindreset.resources.strings.app

import epicarchitect.mindreset.language.AppLanguage
import epicarchitect.mindreset.resources.strings.appDashboard.EnglishAppDashboardStrings
import epicarchitect.mindreset.resources.strings.format.duration.EnglishDurationFormattingStrings
import epicarchitect.mindreset.resources.strings.habits.dashboard.EnglishHabitDashboardStrings
import epicarchitect.mindreset.resources.strings.habits.editing.EnglishHabitEditingStrings
import epicarchitect.mindreset.resources.strings.habits.eventRecords.dashboard.EnglishHabitEventRecordsDashboardStrings
import epicarchitect.mindreset.resources.strings.habits.eventRecords.editing.EnglishHabitEventRecordEditingStrings
import kotlinx.datetime.format.MonthNames

class EnglishAppStrings : AppStrings {
    override val appDashboardStrings = EnglishAppDashboardStrings()
    override val habitDashboardStrings = EnglishHabitDashboardStrings()
    override val habitEditingStrings = EnglishHabitEditingStrings()
    override val habitEventRecordsDashboardStrings = EnglishHabitEventRecordsDashboardStrings()
    override val habitEventRecordEditingStrings = EnglishHabitEventRecordEditingStrings()
    override val durationFormattingStrings = EnglishDurationFormattingStrings()
    override val monthNames = MonthNames.ENGLISH_FULL
}