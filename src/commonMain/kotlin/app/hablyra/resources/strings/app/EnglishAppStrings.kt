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
    override fun habitsTab() = "Habits"
    override fun historyTab() = "History"
    override fun insightsTab() = "Insights"
    override fun settingsTab() = "Settings"
    override fun historyTitle() = "History"
    override fun historyEmptyTitle() = "No events yet"
    override fun historyEmptyBody() = "Events you log will appear here so you can understand your pattern over time."
    override fun insightsTitle() = "Insights"
    override fun insightsEmptyTitle() = "Insights will appear here"
    override fun insightsEmptyBody() = "Log events to build a clearer picture of your progress over time."
    override fun settingsTitle() = "Settings"
    override fun appearanceTitle() = "Appearance"
    override fun systemDefaultTheme() = "System default"
    override fun aboutTitle() = "About Hablyra"
    override fun versionLabel(version: String) = "Version " + version
    override fun weekSummaryTitle() = "This week"
    override fun weekSummaryEvents(count: Int) = if (count == 1) "1 occurrence logged" else count.toString() + " occurrences logged"
    override fun rhythmDescription(activeDays: Int) = activeDays.toString() + " active days in the last 14 days"
    override fun period30Days() = "30 days"
    override fun period3Months() = "3 months"
    override fun periodAll() = "All"
    override fun longestStreakLabel() = "Longest streak"
    override fun streakProgressDescription() = "Current streak compared with longest streak"
    override fun showMoreIcons() = "Show more icons"
    override fun showFewerIcons() = "Show fewer icons"
    override fun closeButtonContentDescription() = "Close"
    override fun quickNow() = "Now"
    override fun quickHourAgo() = "1h ago"
    override fun quickYesterday() = "Yesterday"
    override fun quickPick() = "Pick"
    override fun backButtonContentDescription() = "Back"
    override fun privacyChoicesTitle() = "Privacy choices"
    override fun advertisementLabel() = "Advertisement"
}