package epicarchitect.mindreset.resources

import epicarchitect.mindreset.language.AppLanguage
import epicarchitect.mindreset.language.PlatformLanguageProvider
import epicarchitect.mindreset.resources.strings.app.EnglishAppStrings
import epicarchitect.mindreset.resources.strings.app.RussianAppStrings

class AppResources(
    languageProvider: PlatformLanguageProvider
) {
    val strings = when (languageProvider.language) {
        AppLanguage.RUSSIAN -> RussianAppStrings()
        AppLanguage.ENGLISH -> EnglishAppStrings()
    }
}