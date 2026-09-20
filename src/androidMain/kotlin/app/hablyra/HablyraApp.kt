package app.hablyra

import android.app.Application
import app.hablyra.database.AndroidSqlDriverFactory
import app.hablyra.datetime.format.AndroidDateTimeFormatter
import app.hablyra.environment.AppEnvironment
import app.hablyra.language.ComposeBasedLanguageProvider
import app.hablyra.monetization.AndroidMonetizationManager
import com.google.firebase.FirebaseApp

lateinit var appEnvironment: AppEnvironment
lateinit var monetizationManager: AndroidMonetizationManager

class HablyraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        monetizationManager = AndroidMonetizationManager(this)
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