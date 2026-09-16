package app.hablyra.screens.habits.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.hablyra.design.HablyraCard
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.uikit.Statistics

@Composable
fun HabitDetailsStatisticsCard(
    modifier: Modifier = Modifier,
    state: HabitDetailsScreenState
) {
    val environment = LocalAppEnvironment.current
    val strings = environment.resources.strings.habitDashboardStrings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors

    HablyraCard(modifier) {
        Column(
            modifier = Modifier
                .padding(spacing.space16)
                .fillMaxWidth()
        ) {
            Text(
                text = strings.statisticsTitle(),
                color = colors.contentPrimary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(spacing.space12))

            Statistics(
                modifier = Modifier.fillMaxWidth(),
                statistics = state.statistics
            )
        }
    }
}
