package epicarchitect.mindreset

import android.app.Application
import epicarchitect.mindreset.database.AndroidSqlDriverFactory
import epicarchitect.mindreset.datetime.format.AndroidDateTimeFormatter
import epicarchitect.mindreset.environment.AppEnvironment
import epicarchitect.mindreset.language.ComposeBasedLanguageProvider

lateinit var appEnvironment: AppEnvironment

class MindResetApp : Application() {
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