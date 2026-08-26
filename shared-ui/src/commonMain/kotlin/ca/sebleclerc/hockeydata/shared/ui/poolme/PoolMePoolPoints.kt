package ca.sebleclerc.hockeydata.shared.ui.poolme

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay.RowItem

@Composable
fun PoolMePoolPoints(poolPoints: Float) {
  Row(
    modifier =
      Modifier
        .height(Constants.UI_ROW_HEIGHT.dp),
  ) {
    RowItem(
      text = "       Points totaux;    $poolPoints",
      padding = 500,
    )
  }
}
