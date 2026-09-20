package app.hablyra.screens.root

import kotlinx.serialization.Serializable

sealed interface RootRoute {

    @Serializable
    data object Dashboard : RootRoute

    @Serializable
    data object History : RootRoute

    @Serializable
    data object Insights : RootRoute

    @Serializable
    data object Settings : RootRoute

    @Serializable
    data class HabitDetails(
        val habitId: Int
    ) : RootRoute

    @Serializable
    data class HabitEditing(
        val habitId: Int?
    ) : RootRoute

    @Serializable
    data class HabitEventRecordEditing(
        val habitEventRecordId: Int?,
        val habitId: Int
    ) : RootRoute

    @Serializable
    data class HabitEventRecordsDetails(
        val habitId: Int
    ) : RootRoute
}