package app.hablyra.design

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun <T> SegmentedControl(
    options: List<Pair<T, String>>,
    selected: T,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = HablyraTheme.colors
    val spacing = HablyraTheme.spacing
    Row(
        modifier = modifier
            .border(1.dp, colors.borderSubtle, MaterialTheme.shapes.medium)
            .padding(spacing.space4),
        horizontalArrangement = Arrangement.spacedBy(spacing.space4)
    ) {
        options.forEach { (value, label) ->
            val isSelected = value == selected
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 44.dp)
                    .semantics { this.selected = isSelected }
                    .selectable(
                        selected = isSelected,
                        role = Role.RadioButton,
                        onClick = { onSelected(value) }
                    ),
                shape = MaterialTheme.shapes.small,
                border = if (isSelected) BorderStroke(2.dp, colors.brandPrimary) else null,
                color = if (isSelected) colors.navigationIndicator else colors.surface,
                contentColor = if (isSelected) colors.brandPrimary else colors.contentSecondary
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = spacing.space8,
                        vertical = spacing.space12
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
