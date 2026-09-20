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
    override fun habitsTab() = "Привычки"
    override fun historyTab() = "История"
    override fun insightsTab() = "Аналитика"
    override fun settingsTab() = "Настройки"
    override fun historyTitle() = "История"
    override fun historyEmptyTitle() = "Событий пока нет"
    override fun historyEmptyBody() = "Записанные события появятся здесь и помогут понять ваши закономерности."
    override fun insightsTitle() = "Аналитика"
    override fun insightsEmptyTitle() = "Здесь появится аналитика"
    override fun insightsEmptyBody() = "Записывайте события, чтобы увидеть прогресс с течением времени."
    override fun settingsTitle() = "Настройки"
    override fun appearanceTitle() = "Оформление"
    override fun systemDefaultTheme() = "Как в системе"
    override fun aboutTitle() = "О Hablyra"
    override fun versionLabel(version: String) = "Версия " + version
    override fun weekSummaryTitle() = "На этой неделе"
    override fun weekSummaryEvents(count: Int) = "Событий отмечено: " + count
    override fun rhythmDescription(activeDays: Int) = "Активных дней за последние 14 дней: " + activeDays
    override fun period30Days() = "30 дней"
    override fun period3Months() = "3 месяца"
    override fun periodAll() = "Всё"
    override fun longestStreakLabel() = "Самая длинная серия"
    override fun streakProgressDescription() = "Текущая серия по сравнению с самой длинной"
    override fun showMoreIcons() = "Показать больше значков"
    override fun showFewerIcons() = "Показать меньше значков"
    override fun closeButtonContentDescription() = "Закрыть"
    override fun quickNow() = "Сейчас"
    override fun quickHourAgo() = "1 ч назад"
    override fun quickYesterday() = "Вчера"
    override fun quickPick() = "Выбрать"
    override fun backButtonContentDescription() = "Назад"
    override fun privacyChoicesTitle() = "Настройки конфиденциальности"
    override fun advertisementLabel() = "Реклама"
}