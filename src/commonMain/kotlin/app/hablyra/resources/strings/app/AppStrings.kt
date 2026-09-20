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
    fun habitsTab(): String
    fun historyTab(): String
    fun insightsTab(): String
    fun settingsTab(): String
    fun historyTitle(): String
    fun historyEmptyTitle(): String
    fun historyEmptyBody(): String
    fun insightsTitle(): String
    fun insightsEmptyTitle(): String
    fun insightsEmptyBody(): String
    fun settingsTitle(): String
    fun appearanceTitle(): String
    fun systemDefaultTheme(): String
    fun aboutTitle(): String
    fun versionLabel(version: String): String
    fun weekSummaryTitle(): String
    fun weekSummaryEvents(count: Int): String
    fun rhythmDescription(activeDays: Int): String
    fun period30Days(): String
    fun period3Months(): String
    fun periodAll(): String
    fun longestStreakLabel(): String
    fun streakProgressDescription(): String
    fun showMoreIcons(): String
    fun showFewerIcons(): String
    fun closeButtonContentDescription(): String
    fun quickNow(): String
    fun quickHourAgo(): String
    fun quickYesterday(): String
    fun quickPick(): String
    fun backButtonContentDescription(): String
    fun privacyChoicesTitle(): String
    fun advertisementLabel(): String
}

