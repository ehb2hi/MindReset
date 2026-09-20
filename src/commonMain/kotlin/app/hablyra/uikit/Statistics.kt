package app.hablyra.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.hablyra.design.HablyraTheme

data class StatisticData(
    val name: String,
    val value: String
)

@Composable
fun Statistics(
    modifier: Modifier = Modifier,
    statistics: List<StatisticData>
) {
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.space8)
    ) {
        statistics.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = spacing.space16)
                        .weight(1f),
                    text = item.name,
                    color = colors.contentSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.value,
                    color = colors.contentPrimary,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            if (item != statistics.last()) {
                HorizontalDivider(color = colors.divider)
            }
        }
    }
}
