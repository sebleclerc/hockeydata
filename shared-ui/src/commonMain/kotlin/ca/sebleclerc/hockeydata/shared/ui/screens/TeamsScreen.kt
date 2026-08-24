package ca.sebleclerc.hockeydata.shared.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.helpers.Constants
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
    actions = { Button(onClick = { onAction(TeamsAction.RefreshRosters) }){ Text("Update Rosters")} },
    listHeader = { TeamsHeader() }
  ) {
    items(state.data.size) { index ->
      val team = state.data[index].first
      val proportion = state.data[index].second

      Row(
        modifier = Modifier
          .height(Constants.UI_ROW_HEIGHT.dp)
      ) {
        RowItem(text = team.id.toString(), padding = Constants.UI_PADDING_ID)
        RowItem(text = team.name, padding = Constants.UI_PADDING_NAME)
        RowItem(text = proportion, padding = Constants.UI_PADDING_PROPORTION)
      }
    }
  }
}
