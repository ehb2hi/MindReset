package app.hablyra.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.hablyra.design.EmptyState
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute

@Composable
fun DashboardScreen() {
    val state = rememberDashboardScreenState() ?: return

    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val strings = environment.resources.strings.appDashboardStrings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    val onAddHabitClick = {
        navController.navigate(
            RootRoute.HabitEditing(
                habitId = null
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = spacing.space24)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal),
            verticalArrangement = Arrangement.spacedBy(spacing.space4)
        ) {
            Text(
                text = strings.titleText(),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = strings.subtitleText(),
                color = colors.contentSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state.habits.isEmpty()) {
                EmptyState(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = spacing.screenHorizontal),
                    title = strings.emptyHabitsTitle(),
                    description = strings.emptyHabitsText(),
                    action = {
                        HablyraPrimaryButton(onClick = onAddHabitClick) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                            Text(text = strings.newHabitButtonText())
                        }
                    }
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = spacing.screenHorizontal,
                        end = spacing.screenHorizontal,
                        top = spacing.space24,
                        bottom = spacing.space40 + spacing.space40
                    ),
                    verticalArrangement = Arrangement.spacedBy(spacing.space16)
                ) {
                    items(
                        items = state.habits,
                        key = { it.id }
                    ) {
                        HabitCard(it)
                    }
                }

                HablyraPrimaryButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(spacing.space20),
                    onClick = onAddHabitClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                    Text(text = strings.newHabitButtonText())
                }
            }
        }
    }
}
