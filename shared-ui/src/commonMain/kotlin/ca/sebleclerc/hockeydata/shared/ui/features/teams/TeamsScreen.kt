package ca.sebleclerc.hockeydata.shared.ui.features.teams

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay.RowButton
import ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay.RowItem
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageLayout

@Composable
fun TeamsScreen(
  state: TeamsState,
  onAction: (TeamsAction) -> Unit) {
  PageLayout(
    title = "Teams",
    toolbar = { TeamActionsView(onAction) },
    listHeader = { TeamsHeader() }
  ) {
    items(state.data.size) { index ->
      val team = state.data[index].first
      val dbProportion = state.data[index].second
      val salaryProportion = state.data[index].third

      Row(
        modifier = Modifier
          .height(35.dp)
      ) {
        RowItem(text = team.id.toString(), padding = Constants.UI_PADDING_ID)
        RowItem(text = team.name, padding = Constants.UI_PADDING_NAME)
        RowItem(text = dbProportion, padding = Constants.UI_PADDING_PROPORTION)
        RowItem(text = salaryProportion, padding = Constants.UI_PADDING_PROPORTION)
        RowButton(text = "Cache") {
          onAction(TeamsAction.CacheTeam(team.id))
        }
      }
    }

    item {
      Box(
        modifier = Modifier
          .padding(vertical = 15.dp)
      ){
        RowItem(
          text = "Total player salaries proportion: ${state.totalSalaryProportion}",
          padding = 500
        )
      }
    }
  }
}
