package app.hablyra.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.hablyra.design.EmptyState
import app.hablyra.design.HablyraTheme
import app.hablyra.design.LoopMark
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.screens.habits.eventRecords.details.HabitRecordItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Composable
fun HistoryScreen() {
    val environment = LocalAppEnvironment.current
    val strings = environment.resources.strings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val records = remember {
        environment.database.habitEventRecordQueries.records().asFlow().mapToList(Dispatchers.IO)
    }.collectAsState(null).value ?: return
    val habits = remember {
        environment.database.habitQueries.habits().asFlow().mapToList(Dispatchers.IO)
    }.collectAsState(null).value ?: return
    val habitNames = remember(habits) { habits.associate { it.id to it.name } }
    val sortedRecords = remember(records) { records.sortedByDescending { it.endTime } }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(
                start = spacing.screenHorizontal,
                end = spacing.screenHorizontal,
                top = spacing.space24,
                bottom = spacing.space16
            ),
            text = strings.historyTitle(),
            color = colors.contentPrimary,
            style = MaterialTheme.typography.headlineLarge
        )
        if (sortedRecords.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(horizontal = spacing.screenHorizontal),
                contentAlignment = Alignment.Center
            ) {
                EmptyState(
                    visual = { LoopMark() },
                    title = strings.historyEmptyTitle(),
                    description = strings.historyEmptyBody()
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = spacing.screenHorizontal,
                    end = spacing.screenHorizontal,
                    bottom = spacing.space40
                ),
                verticalArrangement = Arrangement.spacedBy(spacing.space12)
            ) {
                items(sortedRecords, key = { it.id }) { record ->
                    HabitRecordItem(
                        item = record,
                        habitName = habitNames[record.habitId]
                    )
                }
            }
        }
    }
}
