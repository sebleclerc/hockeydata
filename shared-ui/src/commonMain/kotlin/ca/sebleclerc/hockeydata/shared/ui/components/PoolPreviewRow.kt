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
    LazyColumnRowItem(text = player.player.id.toString(), padding = Constants.UI_PADDING_ID)
    LazyColumnRowItem(text = player.player.fullName, padding = Constants.UI_PADDING_NAME)
    LazyColumnRowItem(text = player.player.positionCode, padding = Constants.UI_PADDING_POSITION)
    LazyColumnRowItem(
      text = player.team?.abbreviation ?: "N/A",
      padding = Constants.UI_PADDING_TEAM_ABBREV,
    )
    LazyColumnRowItem(text = player.salary?.avv ?: "N/A", padding = Constants.UI_PADDING_AVV)
    LazyColumnRowItem(
      text = (player.current?.poolPoints ?: 0F).toString(),
      padding = Constants.UI_PADDING_ID,
    )
    LazyColumnRowItem(
      text = Formatter.roundDouble(player.averagePoints),
      padding = Constants.UI_PADDING_ID,
    )
    LazyColumnRowItem(text = player.poolValue, padding = Constants.UI_PADDING_ID)
    LazyColumnRowItem(text = player.averagePoolValue, padding = Constants.UI_PADDING_ID)
    player.history.forEach {
      LazyColumnRowItem(
        text = it,
        padding = Constants.UI_PADDING_HISTORY,
      )
    }
    LazyColumnRowButton(
      text = "Taken",
      onClick = { onAction(PoolPreviewAction.OnPlayerTaken(player)) },
    )
    LazyColumnRowButton(
      text = "Select",
      onClick = { onAction(PoolPreviewAction.OnPlayerSelect(player)) }
    )
  }
}
