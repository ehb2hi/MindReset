package app.hablyra.screens.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.AppEnvironment
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.screens.dashboard.DashboardScreen
import app.hablyra.screens.habits.details.HabitDetailsScreen
import app.hablyra.screens.habits.editing.HabitEditingScreen
import app.hablyra.screens.habits.eventRecords.details.HabitEventRecordsDetailsScreen
import app.hablyra.screens.habits.eventRecords.editing.HabitEventRecordEditingScreen
import app.hablyra.screens.history.HistoryScreen
import app.hablyra.screens.insights.InsightsScreen
import app.hablyra.screens.settings.SettingsScreen

val LocalRootNavController = staticCompositionLocalOf<NavController> {
    error("LocalRootNavController not present")
}

@Composable
fun RootScreen(environment: AppEnvironment) {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalAppEnvironment provides environment,
        LocalRootNavController provides navController
    ) {
        HablyraTheme {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val destination = backStackEntry?.destination
            Surface(color = MaterialTheme.colorScheme.background) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (destination.isTopLevelDestination()) {
                            HablyraBottomNavigation(destination, navController)
                        }
                    }
                ) { contentPadding ->
                    NavHost(
                        modifier = Modifier.fillMaxSize().padding(contentPadding),
                        navController = navController,
                        startDestination = RootRoute.Dashboard
                    ) {
                        composable<RootRoute.Dashboard> { DashboardScreen() }
                        composable<RootRoute.History> { HistoryScreen() }
                        composable<RootRoute.Insights> { InsightsScreen() }
                        composable<RootRoute.Settings> { SettingsScreen() }
                        composable<RootRoute.HabitDetails> {
                            val route: RootRoute.HabitDetails = it.toRoute()
                            HabitDetailsScreen(route.habitId)
                        }
                        composable<RootRoute.HabitEditing> {
                            val route: RootRoute.HabitEditing = it.toRoute()
                            HabitEditingScreen(route.habitId)
                        }
                        composable<RootRoute.HabitEventRecordsDetails> {
                            val route: RootRoute.HabitEventRecordsDetails = it.toRoute()
                            HabitEventRecordsDetailsScreen(route.habitId)
                        }
                        composable<RootRoute.HabitEventRecordEditing> {
                            val route: RootRoute.HabitEventRecordEditing = it.toRoute()
                            HabitEventRecordEditingScreen(route.habitEventRecordId, route.habitId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HablyraBottomNavigation(destination: NavDestination?, navController: NavController) {
    val strings = LocalAppEnvironment.current.resources.strings
    val colors = HablyraTheme.colors
    val items = listOf(
        BottomDestination(RootRoute.Dashboard, strings.habitsTab(), Icons.Outlined.Home, Icons.Filled.Home),
        BottomDestination(RootRoute.History, strings.historyTab(), Icons.Outlined.History, Icons.Filled.History),
        BottomDestination(RootRoute.Insights, strings.insightsTab(), Icons.Outlined.BarChart, Icons.Filled.BarChart),
        BottomDestination(RootRoute.Settings, strings.settingsTab(), Icons.Outlined.Settings, Icons.Filled.Settings)
    )
    NavigationBar(containerColor = colors.surface, contentColor = colors.contentPrimary) {
        items.forEach { item ->
            val selected = item.route.matches(destination)
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(RootRoute.Dashboard) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.icon,
                        contentDescription = item.label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.brandPrimary,
                    selectedTextColor = colors.brandPrimary,
                    indicatorColor = colors.navigationIndicator,
                    unselectedIconColor = colors.contentSecondary,
                    unselectedTextColor = colors.contentSecondary
                ),
                label = { Text(item.label) }
            )
        }
    }
}

private data class BottomDestination(
    val route: RootRoute,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector
)

private fun RootRoute.matches(destination: NavDestination?) = when (this) {
    RootRoute.Dashboard -> destination?.route == RootRoute.Dashboard::class.qualifiedName
    RootRoute.History -> destination?.route == RootRoute.History::class.qualifiedName
    RootRoute.Insights -> destination?.route == RootRoute.Insights::class.qualifiedName
    RootRoute.Settings -> destination?.route == RootRoute.Settings::class.qualifiedName
    else -> false
}

private fun NavDestination?.isTopLevelDestination() =
    RootRoute.Dashboard.matches(this) ||
        RootRoute.History.matches(this) ||
        RootRoute.Insights.matches(this) ||
        RootRoute.Settings.matches(this)
