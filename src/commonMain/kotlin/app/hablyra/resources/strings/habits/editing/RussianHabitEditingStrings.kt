package app.hablyra.resources.strings.habits.editing

import app.hablyra.habits.HabitNewNameError

class RussianHabitEditingStrings : HabitEditingStrings {
    override fun titleText(isNewHabit: Boolean) =
        if (isNewHabit) "Добавить привычку" else "Редактировать привычку"

    override fun habitNameDescription() = "Используйте короткое название, которое легко узнать."
    override fun habitNameTitle() = "Название привычки"
    override fun habitIconTitle() = "Иконка"
    override fun habitIconDescription() = "Выберите иконку, по которой привычку легко найти."
    override fun finishButtonText(isNewHabit: Boolean) = if (isNewHabit) "Сохранить привычку" else "Сохранить изменения"
    override fun habitNameError(error: HabitNewNameError) = when (error) {
        HabitNewNameError.AlreadyUsed -> "Это название уже используется."
        HabitNewNameError.Empty -> "Введите название привычки."
        is HabitNewNameError.TooLong -> {
            "Название не может быть длиннее чем ${error.maxLength} символов."
        }
    }

    override fun deleteConfirmation() = "Удалить эту привычку?"
    override fun cancel() = "Отмена"
    override fun confirmDeleteButton() = "Удалить"
    override fun deleteDescription() = "Привычка и история событий будут удалены с этого устройства."
    override fun iconContentDescription(iconId: Int, selected: Boolean) = if (selected) "Иконка " + iconId + " выбрана" else "Выбрать иконку " + iconId
    override fun deleteButton() = "Удалить привычку"
}