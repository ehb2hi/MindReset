package app.hablyra.uikit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraTheme

@Composable
fun InputCard(
    title: String,
    description: String,
    error: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val horizontalPadding = PaddingValues(horizontal = spacing.space16)
    HablyraCard(modifier) {
        Spacer(modifier = Modifier.height(spacing.space16))
        Text(
            modifier = Modifier.padding(horizontalPadding),
            text = title,
            color = colors.contentPrimary,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(spacing.space4))
        Text(
            modifier = Modifier.padding(horizontalPadding),
            text = description,
            color = colors.contentSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(spacing.space12))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.space16),
            content = content
        )

        AnimatedVisibility(
            visible = error != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            val color by animateColorAsState(
                targetValue = if (error != null) {
                    colors.error
                } else {
                    Color.Unspecified
                },
            )
            Text(
                modifier = Modifier
                    .padding(horizontalPadding)
                    .padding(top = spacing.space8),
                text = error ?: "",
                color = color,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(spacing.space16))
    }
}
