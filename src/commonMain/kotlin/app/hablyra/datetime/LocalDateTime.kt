package app.hablyra.datetime

import kotlinx.datetime.LocalDateTime

fun ClosedRange<LocalDateTime>.toLocalDateRange() = start.date..endInclusive.date