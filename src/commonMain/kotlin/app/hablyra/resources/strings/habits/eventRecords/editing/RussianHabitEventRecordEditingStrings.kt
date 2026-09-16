package app.hablyra.resources.strings.habits.eventRecords.editing

import app.hablyra.habits.HabitEventCountError
import app.hablyra.habits.HabitEventRecordTimeRangeError

class RussianHabitEventRecordEditingStrings : HabitEventRecordEditingStrings {
    override fun titleText(
        isNewRecord: Boolean,
        habitName: String
    ) = if (isNewRecord) {
        "Записать событие - " + habitName
    } else {
        "Редактировать событие - " + habitName
    }

    override fun commentDescription() = "Вы можете написать комментарий, но это не обязательно."
    override fun commentTitle() = "Комментарий"
    override fun finishDescription() = "Вы всегда сможете изменить или удалить это событие."
    override fun finishButton() = "Сохранить событие"
    override fun deleteConfirmation() = "Удалить это событие?"
    override fun yes() = "Да"
    override fun cancel() = "Отмена"
    override fun deleteDescription() = "Событие будет удалено из истории этой привычки."
    override fun deleteButton() = "Удалить событие"
    override fun confirmDeleteButton() = "Удалить"
    override fun startDateTimeLabel() = "Начало"
    override fun endDateTimeLabel() = "Конец"
    override fun done() = "Готово"
    override fun inputDateTimeAsRangeCheckbox() = "Указать как временной диапазон"

    override fun eventCountError(error: HabitEventCountError) = when (error) {
        HabitEventCountError.Empty -> {
            "Введите количество событий"
        }
    }

    override fun timeRangeTitle() = "Дата и время"
    override fun timeRangeError(error: HabitEventRecordTimeRangeError) = when (error) {
        HabitEventRecordTimeRangeError.BiggestThenCurrentTime -> "Дата и время не могут быть больше текущего времени."
    }

    override fun eventCountTitle() = "События"
    override fun eventCountDescription() = "Введите, сколько событий произошло."
    override fun timeRangeDescription() = "Выберите, когда произошло событие."
}