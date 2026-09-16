package app.hablyra.uikit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import app.hablyra.design.HablyraTheme
import kotlin.math.ceil

@Composable
fun <T> SingleSelectionGrid(
    modifier: Modifier = Modifier,
    items: List<T>,
    cell: @Composable BoxScope.(T) -> Unit,
    selectedItem: T?,
    onSelect: (T) -> Unit,
    countInRow: Int = 5,
    itemContentDescription: ((item: T, selected: Boolean) -> String)? = null
) {
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val countRows = ceil((items.size / countInRow.toFloat())).toInt()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.space8)
    ) {
        repeat(countRows) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.space8)
            ) {
                repeat(countInRow) { itemIndex ->
                    val item = items.getOrNull(rowIndex * countInRow + itemIndex)
                    if (item != null) {
                        val isSelected = item == selectedItem
                        val semanticsModifier = itemContentDescription?.let { descriptionProvider ->
                            Modifier.semantics {
                                selected = isSelected
                                contentDescription = descriptionProvider(item, isSelected)
                            }
                        } ?: Modifier

                        Card(
                            modifier = Modifier
                                .size(spacing.space40 + spacing.space8)
                                .then(semanticsModifier),
                            shape = MaterialTheme.shapes.small,
                            onClick = {
                                onSelect(item)
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) {
                                    colors.brandSecondary
                                } else {
                                    colors.surfaceSubtle
                                },
                                contentColor = if (isSelected) {
                                    colors.brandPrimary
                                } else {
                                    colors.contentSecondary
                                }
                            ),
                            border = BorderStroke(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) colors.brandPrimary else colors.borderSubtle
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                cell(item)
                                if (isSelected) {
                                    Icon(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(spacing.space16),
                                        imageVector = Icons.Filled.Check,
                                        tint = colors.brandPrimary,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
