package app.hablyra

import androidx.compose.ui.window.ComposeUIViewController
import app.hablyra.database.IosSqlDriverFactory
import app.hablyra.datetime.format.IosDateTimeFormatter
import app.hablyra.environment.AppEnvironment
import app.hablyra.language.ComposeBasedLanguageProvider
import app.hablyra.screens.root.RootScreen

val defaultAppEnvironment by lazy {
    AppEnvironment(
        platformSqlDriverFactory = IosSqlDriverFactory(),
        platformLanguageProvider = ComposeBasedLanguageProvider(),
        platformDateTimeFormatter = IosDateTimeFormatter()
    )
}


@Suppress("unused")
fun createAppViewController() = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    },
    content = {
        RootScreen(defaultAppEnvironment)
    }
)