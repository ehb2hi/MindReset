package app.hablyra.resources.strings.habits.editing

import app.hablyra.habits.HabitNewNameError

class EnglishHabitEditingStrings : HabitEditingStrings {
    override fun titleText(isNewHabit: Boolean) =
        if (isNewHabit) "Add habit" else "Edit habit"

    override fun habitNameDescription() = "Use a short name you will recognize quickly."
    override fun habitNameTitle() = "Habit name"
    override fun habitIconTitle() = "Icon"
    override fun habitIconDescription() = "Choose an icon that makes this habit easy to spot."
    override fun finishButtonText(isNewHabit: Boolean) = if (isNewHabit) "Save habit" else "Save changes"
    override fun habitNameError(error: HabitNewNameError) = when (error) {
        HabitNewNameError.AlreadyUsed -> "This name has already been used."
        HabitNewNameError.Empty -> "Enter a habit name."
        is HabitNewNameError.TooLong -> {
            "The name cannot be longer than ${error.maxLength} characters."
        }
    }

    override fun deleteConfirmation() = "Delete this habit?"
    override fun cancel() = "Cancel"
    override fun confirmDeleteButton() = "Delete"
    override fun deleteDescription() = "This removes the habit and its event history from this device."
    override fun iconContentDescription(iconId: Int, selected: Boolean) = if (selected) "Icon " + iconId + " selected" else "Select icon " + iconId
    override fun deleteButton() = "Delete habit"
}