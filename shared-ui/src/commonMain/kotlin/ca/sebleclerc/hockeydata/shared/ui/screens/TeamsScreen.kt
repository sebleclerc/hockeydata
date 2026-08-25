package ca.sebleclerc.hockeydata.shared.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.shared.ui.components.TeamActionsView
import ca.sebleclerc.hockeydata.shared.ui.components.TeamsHeader
import ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay.RowItem
import ca.sebleclerc.hockeydata.shared.ui.components.page.PageLayout
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.TeamsAction
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.TeamsState

@Composable
fun TeamsScreen(
  state: TeamsState,
  onAction: (TeamsAction) -> Unit) {
  PageLayout(
    title = "Teams",
    actions = { TeamActionsView(onAction) },
    listHeader = { TeamsHeader() }
  ) {
    items(state.data.size) { index ->
      val team = state.data[index].first
      val dbProportion = state.data[index].second
      val salaryProportion = state.data[index].third

      Row(
        modifier = Modifier
          .height(Constants.UI_ROW_HEIGHT.dp)
      ) {
        RowItem(text = team.id.toString(), padding = Constants.UI_PADDING_ID)
        RowItem(text = team.name, padding = Constants.UI_PADDING_NAME)
        RowItem(text = dbProportion, padding = Constants.UI_PADDING_PROPORTION)
        RowItem(text = salaryProportion, padding = Constants.UI_PADDING_PROPORTION)
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
