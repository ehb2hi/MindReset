package epicarchitect.mindreset.datetime.format

import epicarchitect.mindreset.format.PlatformDateTimeFormatter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class IosDateTimeFormatter : PlatformDateTimeFormatter {
    override fun format(date: LocalDate) = date.toString()
    override fun format(time: LocalTime) = time.toString()
}