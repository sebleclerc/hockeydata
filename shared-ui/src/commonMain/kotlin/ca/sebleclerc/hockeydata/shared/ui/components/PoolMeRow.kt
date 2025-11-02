package ca.sebleclerc.hockeydata.shared.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.domain.PoolMePlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay.RowItem

@Composable
fun PoolMeRow(player: PoolMePlayer) {
  Row(
    modifier =
      Modifier
        .height(Constants.UI_ROW_HEIGHT.dp),
  ) {
    RowItem(text = player.player.id.toString(), padding = Constants.UI_PADDING_ID)
    RowItem(text = player.player.fullName, padding = Constants.UI_PADDING_NAME)
    RowItem(text = player.games.toString(), padding = Constants.UI_PADDING_ID)
    RowItem(text = player.goals.toString(), padding = Constants.UI_PADDING_ID)
    RowItem(text = player.assists.toString(), padding = Constants.UI_PADDING_ID)
    RowItem(text = player.points.toString(), padding = Constants.UI_PADDING_ID)
    RowItem(text = player.poolPoints.toString(), padding = Constants.UI_PADDING_ID)
    RowItem(text = player.avv, padding = Constants.UI_PADDING_AVV)
  }
}
