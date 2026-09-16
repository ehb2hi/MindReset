package app.hablyra.environment

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.hablyra.database.AppDatabase
import app.hablyra.database.Habit
import app.hablyra.database.HabitEventRecord
import app.hablyra.database.InstantAdapter
import app.hablyra.database.PlatformSqlDriverFactory
import app.hablyra.datetime.AppDateTime
import app.hablyra.format.AppFormat
import app.hablyra.format.PlatformDateTimeFormatter
import app.hablyra.habits.HabitsEnvironment
import app.hablyra.language.PlatformLanguageProvider
import app.hablyra.resources.AppResources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AppEnvironment(
    platformSqlDriverFactory: PlatformSqlDriverFactory,
    platformLanguageProvider: PlatformLanguageProvider,
    platformDateTimeFormatter: PlatformDateTimeFormatter
) {
    val database = AppDatabase(
        driver = platformSqlDriverFactory.create(
            schema = AppDatabase.Schema,
            databaseName = "hablyra.db"
        ),
        HabitAdapter = Habit.Adapter(
            idAdapter = IntColumnAdapter,
            iconIdAdapter = IntColumnAdapter
        ),
        HabitEventRecordAdapter = HabitEventRecord.Adapter(
            idAdapter = IntColumnAdapter,
            habitIdAdapter = IntColumnAdapter,
            startTimeAdapter = InstantAdapter,
            endTimeAdapter = InstantAdapter,
            eventCountAdapter = IntColumnAdapter
        )
    )
    val dateTime = AppDateTime()
    val habits = HabitsEnvironment(
        coroutineScope = CoroutineScope(Dispatchers.Default),
        dateTime = dateTime
    )
    val resources = AppResources(platformLanguageProvider)
    val format = AppFormat(
        resources = resources,
        platformDateTimeFormatter = platformDateTimeFormatter
    )
}