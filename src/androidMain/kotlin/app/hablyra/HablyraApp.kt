package app.hablyra

import android.app.Application
import app.hablyra.database.AndroidSqlDriverFactory
import app.hablyra.datetime.format.AndroidDateTimeFormatter
import app.hablyra.environment.AppEnvironment
import app.hablyra.language.ComposeBasedLanguageProvider

lateinit var appEnvironment: AppEnvironment

class HablyraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val languageProvider = ComposeBasedLanguageProvider()
        appEnvironment = AppEnvironment(
            platformSqlDriverFactory = AndroidSqlDriverFactory(
                context = this
            ),
            platformLanguageProvider = languageProvider,
            platformDateTimeFormatter = AndroidDateTimeFormatter(
                context = this,
                languageProvider = languageProvider
            )
        )
    }
}