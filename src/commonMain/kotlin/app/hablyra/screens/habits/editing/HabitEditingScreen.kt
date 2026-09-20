package app.hablyra.screens.habits.editing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import app.hablyra.design.HablyraPrimaryButton
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.habits.checkHabitNewName
import app.hablyra.screens.root.LocalRootNavController
import app.hablyra.screens.root.RootRoute
import app.hablyra.uikit.SimpleScrollableScreen
import app.hablyra.uikit.SingleSelectionGrid

@Composable
fun HabitEditingScreen(habitId: Int? = null) {
    val state = rememberHabitEditingScreenState(habitId) ?: return
    val environment = LocalAppEnvironment.current
    val navController = LocalRootNavController.current
    val strings = environment.resources.strings.habitEditingStrings
    val appStrings = environment.resources.strings
    val habitIcons = environment.habits.icons
    val habitQueries = environment.database.habitQueries
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val isNewHabit = state.initialHabit == null
    val keyboard = LocalSoftwareKeyboardController.current
    var showDeletion by remember { mutableStateOf(false) }
    var iconsExpanded by remember { mutableStateOf(false) }

    if (showDeletion) {
        HabitDeletionDialog(
            habitId = habitId!!,
            onDismiss = { showDeletion = false },
            onDeleted = {
                navController.popBackStack(route = RootRoute.Dashboard, inclusive = false)
            }
        )
    }

    val saveHabit = save@{
        state.habitNameError = checkHabitNewName(
            newName = state.habitName,
            initialName = state.initialHabit?.name,
            maxLength = environment.habits.rules.maxHabitNameLength,
            nameIsExists = { habitQueries.countWithName(it).executeAsOne() > 0L }
        )
        if (state.habitNameError != null) return@save
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

    SimpleScrollableScreen(
        title = strings.titleText(isNewHabit),
        onBackClick = navController::popBackStack,
        footer = {
            androidx.compose.material3.Surface(
                color = colors.background,
                shadowElevation = 4.dp
            ) {
                HablyraPrimaryButton(
                    modifier = Modifier.fillMaxWidth().padding(
                        horizontal = spacing.screenHorizontal,
                        vertical = spacing.space12
                    ),
                    onClick = saveHabit
                ) {
                    Text(strings.finishButtonText(isNewHabit))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(
                horizontal = spacing.screenHorizontal,
                vertical = spacing.space20
            ),
            verticalArrangement = Arrangement.spacedBy(spacing.space12)
        ) {
            Text(
                text = strings.habitNameTitle(),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = strings.habitNameDescription(),
                color = colors.contentSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.habitName,
                onValueChange = {
                    state.habitName = it
                    state.habitNameError = null
                },
                singleLine = true,
                isError = state.habitNameError != null,
                supportingText = state.habitNameError?.let { error ->
                    { Text(strings.habitNameError(error)) }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboard?.hide() }),
                shape = MaterialTheme.shapes.small
            )

            Spacer(Modifier.height(spacing.space8))
            Text(
                text = strings.habitIconTitle(),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = strings.habitIconDescription(),
                color = colors.contentSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                modifier = Modifier
                    .size(spacing.space48)
                    .clip(MaterialTheme.shapes.medium)
                    .background(colors.brandSecondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = habitIcons.getById(state.selectedIconId).imageVector,
                    tint = colors.brandPrimary,
                    contentDescription = strings.iconContentDescription(
                        state.selectedIconId,
                        selected = true
                    )
                )
            }
            SingleSelectionGrid(
                countInRow = 6,
                items = if (iconsExpanded) habitIcons.items else habitIcons.items.take(18),
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
                onSelect = { state.selectedIconId = it.id }
            )
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { iconsExpanded = !iconsExpanded }
            ) {
                Text(if (iconsExpanded) appStrings.showFewerIcons() else appStrings.showMoreIcons())
            }

            if (state.initialHabit != null) {
                Spacer(Modifier.height(spacing.space16))
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.error),
                    border = BorderStroke(1.dp, colors.error),
                    onClick = { showDeletion = true }
                ) {
                    Text(strings.deleteButton())
                }
            }
        }
    }
}
