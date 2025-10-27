package ca.sebleclerc.hockeydata.shared.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.core.helpers.Formatter
import ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay.RowButton
import ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay.RowItem
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolPreviewAction

@Composable
fun PoolPreviewRow(
  player: PoolSkaterPlayer,
  onAction: (PoolPreviewAction) -> Unit,
) {
  Row(
    modifier =
      Modifier
        .height(Constants.UI_ROW_HEIGHT.dp)
        .padding(vertical = 2.dp),
  ) {
    RowItem(text = player.player.id.toString(), padding = Constants.UI_PADDING_ID)
    RowItem(text = player.player.fullName, padding = Constants.UI_PADDING_NAME)
    RowItem(text = player.player.positionCode, padding = Constants.UI_PADDING_POSITION)
    RowItem(
      text = player.team?.abbreviation ?: "N/A",
      padding = Constants.UI_PADDING_TEAM_ABBREV,
    )
    RowItem(text = player.salary?.avv ?: "N/A", padding = Constants.UI_PADDING_AVV)
    RowItem(
      text = (player.current?.poolPoints ?: 0F).toString(),
      padding = Constants.UI_PADDING_ID,
    )
    RowItem(
      text = Formatter.roundDouble(player.averagePoints),
      padding = Constants.UI_PADDING_ID,
    )
    RowItem(text = player.poolValue, padding = Constants.UI_PADDING_ID)
    RowItem(text = player.averagePoolValue, padding = Constants.UI_PADDING_ID)
    player.history.forEach {
      RowItem(
        text = it,
        padding = Constants.UI_PADDING_HISTORY,
      )
    }
    RowButton(
      text = "Taken",
      onClick = { onAction(PoolPreviewAction.OnPlayerTaken(player)) },
    )
    RowButton(
      text = "Select",
      onClick = { onAction(PoolPreviewAction.OnPlayerSelect(player)) },
    )
  }
}
