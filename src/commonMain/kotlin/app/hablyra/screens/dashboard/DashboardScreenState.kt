package app.hablyra.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.hablyra.database.Habit
import app.hablyra.database.HabitEventRecord
import app.hablyra.environment.LocalAppEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Immutable
data class DashboardScreenState(
    val habits: List<Habit>,
    val records: List<HabitEventRecord>
)

@Composable
fun rememberDashboardScreenState(): DashboardScreenState? {
    val environment = LocalAppEnvironment.current
    val habitQueries = environment.database.habitQueries
    val recordQueries = environment.database.habitEventRecordQueries
    val habits = remember {
        habitQueries.habits().asFlow().mapToList(Dispatchers.IO)
    }.collectAsState(null).value
    val records = remember {
        recordQueries.records().asFlow().mapToList(Dispatchers.IO)
    }.collectAsState(null).value
    return remember(habits, records) {
        if (habits == null || records == null) null else DashboardScreenState(habits, records)
    }
}
