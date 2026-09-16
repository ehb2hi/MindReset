package app.hablyra.resources.strings.habits.eventRecords.dashboard

class RussianHabitEventRecordsDashboardStrings : HabitEventRecordsDashboardStrings {
    override fun newTrackButton() = "Записать событие"
    override fun eventCount(count: Int) = "События: " + count
    override fun emptyHistoryTitle() = "Событий пока нет"
    override fun emptyHistoryBody() = "Записанные события появятся здесь, чтобы вы могли понять свой паттерн со временем."
    override fun emptyMonthTitle() = "В этом месяце событий нет"
    override fun emptyMonthBody() = "Выберите другой месяц или запишите событие, когда нужно добавить историю."
    override fun previousMonthContentDescription() = "Предыдущий месяц"
    override fun nextMonthContentDescription() = "Следующий месяц"
    override fun editEventContentDescription() = "Редактировать событие"
}