package app.hablyra.resources.strings.app

import app.hablyra.language.AppLanguage
import app.hablyra.resources.strings.appDashboard.EnglishAppDashboardStrings
import app.hablyra.resources.strings.format.duration.EnglishDurationFormattingStrings
import app.hablyra.resources.strings.habits.dashboard.EnglishHabitDashboardStrings
import app.hablyra.resources.strings.habits.editing.EnglishHabitEditingStrings
import app.hablyra.resources.strings.habits.eventRecords.dashboard.EnglishHabitEventRecordsDashboardStrings
import app.hablyra.resources.strings.habits.eventRecords.editing.EnglishHabitEventRecordEditingStrings
import kotlinx.datetime.format.MonthNames

class EnglishAppStrings : AppStrings {
    override val appDashboardStrings = EnglishAppDashboardStrings()
    override val habitDashboardStrings = EnglishHabitDashboardStrings()
    override val habitEditingStrings = EnglishHabitEditingStrings()
    override val habitEventRecordsDashboardStrings = EnglishHabitEventRecordsDashboardStrings()
    override val habitEventRecordEditingStrings = EnglishHabitEventRecordEditingStrings()
    override val durationFormattingStrings = EnglishDurationFormattingStrings()
    override val monthNames = MonthNames.ENGLISH_FULL
    override fun backButtonContentDescription() = "Back"
}