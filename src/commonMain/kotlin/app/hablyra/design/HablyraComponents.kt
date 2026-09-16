package app.hablyra.design

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun HablyraPrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val colors = HablyraTheme.colors
    Button(
        modifier = modifier.heightIn(min = 52.dp),
        enabled = enabled,
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.brandPrimary,
            contentColor = colors.contentOnPrimary,
            disabledContainerColor = colors.surfaceSubtle,
            disabledContentColor = colors.contentTertiary
        ),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        content = content
    )
}

@Composable
fun HablyraCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = HablyraTheme.colors
    if (onClick == null) {
        Card(
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = colors.surface),
            border = BorderStroke(1.dp, colors.borderSubtle),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            content = content
        )
    } else {
        Card(
            modifier = modifier,
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = colors.surface),
            border = BorderStroke(1.dp, colors.borderSubtle),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            content = content
        )
    }
}

@Composable
fun ProgressMetric(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    val colors = HablyraTheme.colors
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space4)
    ) {
        Text(
            text = label,
            color = colors.contentSecondary,
            style = MaterialTheme.typography.labelLarge,
            textAlign = textAlign
        )
        Text(
            text = value,
            color = colors.contentPrimary,
            style = MaterialTheme.typography.displayLarge,
            textAlign = textAlign
        )
    }
}

@Composable
fun EmptyState(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    visual: @Composable (() -> Unit)? = null,
    action: @Composable (() -> Unit)? = null
) {
    val colors = HablyraTheme.colors
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(HablyraTheme.spacing.space24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(HablyraTheme.spacing.space12)
    ) {
        Text(
            text = title,
            color = colors.contentPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = description,
            color = colors.contentSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        if (action != null) {
            action()
        }
    }
}

@Composable
fun LoopMark(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 72.dp
) {
    val colors = HablyraTheme.colors
    Canvas(modifier = modifier.size(size)) {
        val stroke = size.toPx() * 0.12f
        drawArc(color = colors.brandSecondary, startAngle = 24f, sweepAngle = 286f, useCenter = false, style = Stroke(width = stroke, cap = StrokeCap.Round))
        drawArc(color = colors.brandPrimary, startAngle = 204f, sweepAngle = 94f, useCenter = false, style = Stroke(width = stroke, cap = StrokeCap.Round))
    }
}
