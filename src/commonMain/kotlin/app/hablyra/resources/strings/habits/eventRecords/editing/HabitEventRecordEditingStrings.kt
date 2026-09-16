package app.hablyra.resources.strings.habits.eventRecords.editing

import app.hablyra.habits.HabitEventCountError
import app.hablyra.habits.HabitEventRecordTimeRangeError

interface HabitEventRecordEditingStrings {
    fun titleText(isNewRecord: Boolean, habitName: String): String
    fun commentDescription(): String
    fun commentTitle(): String
    fun finishDescription(): String
    fun finishButton(): String
    fun deleteConfirmation(): String
    fun yes(): String
    fun cancel(): String
    fun deleteDescription(): String
    fun deleteButton(): String
    fun confirmDeleteButton(): String
    fun eventCountTitle(): String
    fun eventCountDescription(): String
    fun eventCountError(error: HabitEventCountError): String
    fun timeRangeDescription(): String
    fun timeRangeError(error: HabitEventRecordTimeRangeError): String
    fun timeRangeTitle(): String
    fun startDateTimeLabel(): String
    fun endDateTimeLabel(): String
    fun done(): String
    fun inputDateTimeAsRangeCheckbox(): String
}



