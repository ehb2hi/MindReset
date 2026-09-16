package app.hablyra.utils

import app.hablyra.language.AppLanguage
import java.util.Locale

fun AppLanguage.asJavaLocale() = when (this) {
    AppLanguage.ENGLISH -> Locale("en")
    AppLanguage.RUSSIAN -> Locale("ru")
}