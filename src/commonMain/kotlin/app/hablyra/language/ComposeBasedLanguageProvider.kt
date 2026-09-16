package app.hablyra.language

import androidx.compose.ui.text.intl.Locale

class ComposeBasedLanguageProvider : PlatformLanguageProvider {
    override val language = Locale.current.language.let {
        when (it) {
            "ru" -> AppLanguage.RUSSIAN
            else -> AppLanguage.ENGLISH
        }
    }
}