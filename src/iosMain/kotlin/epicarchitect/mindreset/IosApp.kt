package epicarchitect.mindreset

import androidx.compose.ui.window.ComposeUIViewController
import epicarchitect.mindreset.database.IosSqlDriverFactory
import epicarchitect.mindreset.datetime.format.IosDateTimeFormatter
import epicarchitect.mindreset.environment.AppEnvironment
import epicarchitect.mindreset.language.ComposeBasedLanguageProvider
import epicarchitect.mindreset.screens.root.RootScreen

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