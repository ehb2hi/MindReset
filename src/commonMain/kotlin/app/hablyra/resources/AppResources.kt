package app.hablyra.resources

import app.hablyra.language.AppLanguage
import app.hablyra.language.PlatformLanguageProvider
import app.hablyra.resources.strings.app.EnglishAppStrings
import app.hablyra.resources.strings.app.RussianAppStrings

class AppResources(
    languageProvider: PlatformLanguageProvider
) {
    val strings = when (languageProvider.language) {
        AppLanguage.RUSSIAN -> RussianAppStrings()
        AppLanguage.ENGLISH -> EnglishAppStrings()
    }
}