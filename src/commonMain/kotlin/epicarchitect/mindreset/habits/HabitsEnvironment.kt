package epicarchitect.mindreset.habits

import epicarchitect.mindreset.datetime.AppDateTime
import kotlinx.coroutines.CoroutineScope

class HabitsEnvironment(
    coroutineScope: CoroutineScope,
    dateTime: AppDateTime
) {
    val rules = HabitsRules()
    val timePulse = HabitsTimePulse(coroutineScope, dateTime)
    val icons = HabitIcons()
}