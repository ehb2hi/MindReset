package app.hablyra.resources.strings.habits.eventRecords.dashboard

class EnglishHabitEventRecordsDashboardStrings : HabitEventRecordsDashboardStrings {
    override fun newTrackButton() = "Log event"
    override fun eventCount(count: Int) = "Occurrences: " + count
    override fun emptyHistoryTitle() = "No events yet"
    override fun emptyHistoryBody() = "Events you log will appear here so you can understand your pattern over time."
    override fun emptyMonthTitle() = "No events this month"
    override fun emptyMonthBody() = "Choose another month or log an event when you need to add history."
    override fun previousMonthContentDescription() = "Previous month"
    override fun nextMonthContentDescription() = "Next month"
    override fun editEventContentDescription() = "Edit event"
}