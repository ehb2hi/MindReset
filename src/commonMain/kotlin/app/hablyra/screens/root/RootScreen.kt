package app.hablyra.screens.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    navController = navController,
                    startDestination = RootRoute.Dashboard
                ) {
                    composable<RootRoute.Dashboard> {
                        DashboardScreen()
                    }
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
                        HabitEventRecordEditingScreen(
                            habitEventRecordId = route.habitEventRecordId,
                            habitId = route.habitId
                        )
                    }
                }
            }
        }
    }
}