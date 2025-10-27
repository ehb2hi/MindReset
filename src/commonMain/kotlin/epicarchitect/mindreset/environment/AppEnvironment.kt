package epicarchitect.mindreset.environment

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import epicarchitect.mindreset.database.AppDatabase
import epicarchitect.mindreset.database.Habit
import epicarchitect.mindreset.database.HabitEventRecord
import epicarchitect.mindreset.database.InstantAdapter
import epicarchitect.mindreset.database.PlatformSqlDriverFactory
import epicarchitect.mindreset.datetime.AppDateTime
import epicarchitect.mindreset.format.AppFormat
import epicarchitect.mindreset.format.PlatformDateTimeFormatter
import epicarchitect.mindreset.habits.HabitsEnvironment
import epicarchitect.mindreset.language.PlatformLanguageProvider
import epicarchitect.mindreset.resources.AppResources
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
            databaseName = "mindreset.db"
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