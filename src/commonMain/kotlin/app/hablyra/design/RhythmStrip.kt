package app.hablyra.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun RhythmStrip(
    days: List<Boolean>,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    require(days.size == 14) { "RhythmStrip requires exactly 14 day positions" }
    val colors = HablyraTheme.colors
    val spacing = HablyraTheme.spacing
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .semantics { this.contentDescription = contentDescription },
        horizontalArrangement = Arrangement.spacedBy(spacing.space4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEach { active ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(if (active) 8.dp else 3.dp)
                    .clip(androidx.compose.material3.MaterialTheme.shapes.small)
                    .background(if (active) colors.calendarActive else colors.calendarInactive)
            )
        }
    }
}
