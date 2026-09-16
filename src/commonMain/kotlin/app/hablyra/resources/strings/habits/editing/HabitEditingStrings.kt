package app.hablyra.resources.strings.habits.editing

import app.hablyra.habits.HabitNewNameError

interface HabitEditingStrings {
    fun titleText(isNewHabit: Boolean): String
    fun habitNameDescription(): String
    fun habitNameTitle(): String
    fun habitIconTitle(): String
    fun habitIconDescription(): String
    fun finishButtonText(isNewHabit: Boolean): String
    fun habitNameError(error: HabitNewNameError): String
    fun deleteConfirmation(): String
    fun cancel(): String
    fun confirmDeleteButton(): String
    fun deleteDescription(): String
    fun iconContentDescription(iconId: Int, selected: Boolean): String
    fun deleteButton(): String
}