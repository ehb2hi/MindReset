package app.hablyra.resources.strings.habits.eventRecords.editing

import app.hablyra.habits.HabitEventCountError
import app.hablyra.habits.HabitEventRecordTimeRangeError

class EnglishHabitEventRecordEditingStrings : HabitEventRecordEditingStrings {
    override fun titleText(
        isNewRecord: Boolean,
        habitName: String
    ) = if (isNewRecord) {
        "Log event - $habitName"
    } else {
        "Edit event - $habitName"
    }

    override fun commentDescription() = "You can write a comment, but you don't have to."
    override fun commentTitle() = "Comment"
    override fun finishDescription() = "You can always change or delete this event."
    override fun finishButton() = "Save event"
    override fun deleteConfirmation() = "Delete this event?"
    override fun deleteDescription() = "This removes the event from this habit history."
    override fun deleteButton() = "Delete event"
    override fun confirmDeleteButton() = "Delete"
    override fun yes() = "Yes"
    override fun cancel() = "Cancel"
    override fun eventCountError(error: HabitEventCountError) = when (error) {
        HabitEventCountError.Empty -> {
            "Enter the number of occurrences"
        }
    }

    override fun startDateTimeLabel() = "Start"
    override fun endDateTimeLabel() = "End"
    override fun done() = "Done"
    override fun inputDateTimeAsRangeCheckbox() = "Add duration / specify time range"

    override fun timeRangeTitle() = "Date and time"
    override fun timeRangeError(error: HabitEventRecordTimeRangeError) = when (error) {
        HabitEventRecordTimeRangeError.BiggestThenCurrentTime -> "The date and time cannot be greater than the current time."
    }

    override fun eventCountDescription() = "How many times did it happen?"
    override fun decreaseEventCountContentDescription() = "Decrease occurrences"
    override fun increaseEventCountContentDescription() = "Increase occurrences"
    override fun eventCountTitle() = "Occurrences"
    override fun timeRangeDescription() = "Usually now. Edit the time only when needed."
    override fun nowLabel() = "Now"
}