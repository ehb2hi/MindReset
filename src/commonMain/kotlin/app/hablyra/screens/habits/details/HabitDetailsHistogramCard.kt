package app.hablyra.screens.habits.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.format.DurationFormatter
import app.hablyra.uikit.Histogram
import kotlin.time.Duration.Companion.seconds

@Composable
fun HabitDetailsHistogramCard(
    modifier: Modifier = Modifier,
    state: HabitDetailsScreenState
) {
    val environment = LocalAppEnvironment.current
    val durationFormatter = environment.format.durationFormatter
    val strings = environment.resources.strings.habitDashboardStrings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    HablyraCard(modifier) {
        Column(
            modifier = Modifier.padding(top = spacing.space16)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = spacing.space16),
                text = strings.abstinenceChartTitle(),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.titleMedium
            )

            Histogram(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                values = state.abstinenceHistogramValues,
                barColor = colors.brandPrimary,
                valueTextColor = colors.contentSecondary,
                valueFormatter = {
                    durationFormatter.format(
                        duration = it.toInt().seconds,
                        accuracy = DurationFormatter.Accuracy.DAYS
                    )
                }
            )
        }
    }
}
