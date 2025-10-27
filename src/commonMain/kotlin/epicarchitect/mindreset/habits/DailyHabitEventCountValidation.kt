package epicarchitect.mindreset.habits

fun checkHabitEventCount(value: Int) = when {
    value <= 0 -> HabitEventCountError.Empty
    else -> null
}

sealed interface HabitEventCountError {
    data object Empty : HabitEventCountError
}