package app.hablyra.datetime.format

import android.content.Context
import android.text.format.DateFormat
import app.hablyra.utils.asJavaLocale
import app.hablyra.language.PlatformLanguageProvider
import app.hablyra.format.PlatformDateTimeFormatter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import java.time.format.DateTimeFormatter

class AndroidDateTimeFormatter(
    private val context: Context,
    private val languageProvider: PlatformLanguageProvider
) : PlatformDateTimeFormatter {
    override fun format(date: LocalDate): String {
        val locale = languageProvider.language.asJavaLocale()
        val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", locale)
        return dateFormatter.format(date.toJavaLocalDate())
    }

    override fun format(time: LocalTime): String {
        val timePattern = if (DateFormat.is24HourFormat(context)) "HH:mm" else "hh:mm a"
        val timeFormatter = DateTimeFormatter.ofPattern(timePattern)
        return timeFormatter.format(time.toJavaLocalTime())
    }
}