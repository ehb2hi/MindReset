package epicarchitect.mindreset.datetime

import kotlinx.datetime.LocalDateTime

fun ClosedRange<LocalDateTime>.toLocalDateRange() = start.date..endInclusive.date