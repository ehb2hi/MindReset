package app.hablyra.screens.habits.editing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.habits.checkHabitNewName
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute
import app.hablyra.uikit.InputCard
import app.hablyra.uikit.SimpleScrollableScreen
import app.hablyra.uikit.SingleSelectionGrid
import app.hablyra.uikit.TextInputCard

@Composable
fun HabitEditingScreen(habitId: Int? = null) {
    val state = rememberHabitEditingScreenState(habitId) ?: return
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val strings = environment.resources.strings.habitEditingStrings
    val habitIcons = environment.habits.icons
    val habitQueries = environment.database.habitQueries
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val isNewHabit = state.initialHabit == null

    var showDeletion by remember { mutableStateOf(false) }
    if (showDeletion) {
        HabitDeletionDialog(
            habitId = habitId!!,
            onDismiss = { showDeletion = false },
            onDeleted = {
                navController.popBackStack(
                    route = RootRoute.Dashboard,
                    inclusive = false
                )
            }
        )
    }

    SimpleScrollableScreen(
        title = strings.titleText(isNewHabit = isNewHabit),
        onBackClick = navController::popBackStack
    ) {
        Spacer(Modifier.height(spacing.space20))

        TextInputCard(
            modifier = Modifier
                .padding(horizontal = spacing.screenHorizontal)
                .fillMaxWidth(),
            value = state.habitName,
            onValueChange = {
                state.habitName = it
                state.habitNameError = null
            },
            title = strings.habitNameTitle(),
            description = strings.habitNameDescription(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            error = state.habitNameError?.let(strings::habitNameError)
        )

        Spacer(Modifier.height(spacing.space16))

        InputCard(
            modifier = Modifier
                .padding(horizontal = spacing.screenHorizontal)
                .fillMaxWidth(),
            title = strings.habitIconTitle(),
            description = strings.habitIconDescription()
        ) {
            SingleSelectionGrid(
                items = habitIcons.items,
                selectedItem = habitIcons.getById(state.selectedIconId),
                cell = { icon ->
                    Icon(
                        modifier = Modifier.size(spacing.space24),
                        imageVector = icon.imageVector,
                        contentDescription = null
                    )
                },
                itemContentDescription = { icon, selected ->
                    strings.iconContentDescription(icon.id, selected)
                },
                onSelect = {
                    state.selectedIconId = it.id
                }
            )
        }

        if (state.initialHabit != null) {
            Spacer(modifier = Modifier.height(spacing.space20))

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.screenHorizontal),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.error
                ),
                border = BorderStroke(1.dp, colors.error),
                onClick = {
                    showDeletion = true
                }
            ) {
                Text(text = strings.deleteButton())
            }
        }

        Spacer(modifier = Modifier.weight(1.0f))
        Spacer(modifier = Modifier.height(spacing.space20))

        HablyraPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal),
            onClick = {
                state.habitNameError = checkHabitNewName(
                    newName = state.habitName,
                    initialName = state.initialHabit?.name,
                    maxLength = environment.habits.rules.maxHabitNameLength,
                    nameIsExists = { habitQueries.countWithName(it).executeAsOne() > 0L }
                )
                if (state.habitNameError != null) return@HablyraPrimaryButton

                if (state.initialHabit != null) {
                    habitQueries.update(
                        id = state.initialHabit.id,
                        name = state.habitName,
                        iconId = state.selectedIconId
                    )
                } else {
                    habitQueries.insert(
                        id = null,
                        name = state.habitName,
                        iconId = state.selectedIconId
                    )
                }

                navController.popBackStack()
            }
        ) {
            Text(text = strings.finishButtonText(isNewHabit))
        }

        Spacer(Modifier.height(spacing.space20))
    }
}
