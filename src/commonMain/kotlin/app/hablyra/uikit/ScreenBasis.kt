package app.hablyra.uikit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.hablyra.environment.LocalAppEnvironment

@Composable
fun SimpleTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    navigationContentDescription: String? = null,
    actions: @Composable () -> Unit = {}
) {
    val strings = LocalAppEnvironment.current.resources.strings

    Surface(
        modifier = Modifier.zIndex(2f).then(modifier),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier.heightIn(min = 56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationContentDescription ?: strings.backButtonContentDescription()
                    )
                }
            } else {
                Spacer(modifier = Modifier.padding(start = 24.dp))
            }

            AnimatedContent(
                targetState = title,
                transitionSpec = {
                    fadeIn(tween(220)).togetherWith(fadeOut(tween(220)))
                }
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            actions()
        }
    }
}

@Composable
fun SimpleScrollableScreen(
    title: String,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    onBackClick: (() -> Unit)? = null,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    navigationContentDescription: String? = null,
    actions: @Composable () -> Unit = {},
    footer: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier.fillMaxSize()) {
        SimpleTopAppBar(
            title = title,
            onBackClick = onBackClick,
            navigationIcon = navigationIcon,
            navigationContentDescription = navigationContentDescription,
            actions = actions
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            content = content
        )

        if (footer != null) {
            footer()
        }
    }
}