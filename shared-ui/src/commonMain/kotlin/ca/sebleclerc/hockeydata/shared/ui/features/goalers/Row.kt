package ca.sebleclerc.hockeydata.shared.ui.features.goalers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.domain.PoolGoalerPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.core.helpers.Formatter
import ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay.RowItem
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun Row(goaler: PoolGoalerPlayer) {
  var isHovered by remember { mutableStateOf(false) }

  val poolValueString = BigDecimal(goaler.poolValue)
    .setScale(5, RoundingMode.HALF_EVEN)
    .toString()

  Row(
    modifier = Modifier
      .height(Constants.UI_ROW_HEIGHT.dp)
      .padding(vertical = 2.dp)
      .background(if (isHovered) Color.LightGray else Color.Transparent)
  ) {
    RowItem(
      text = goaler.player.id.toString(),
      padding = Constants.UI_PADDING_ID
    )
    RowItem(
      text = goaler.player.fullName,
      padding = Constants.UI_PADDING_NAME
    )
    RowItem(
      text = goaler.team?.abbreviation ?: "N/A",
      padding = Constants.UI_PADDING_TEAM_ABBREV,
    )
    RowItem(
      text = goaler.salary?.avv ?: "N/A",
      padding = Constants.UI_PADDING_AVV
    )
    RowItem(
      text = (goaler.current?.poolPoints ?: 0F).toString(),
      padding = Constants.UI_PADDING_CURRENT,
    )
    RowItem(
      text = Formatter.roundDouble(goaler.averagePoints),
      padding = Constants.UI_PADDING_AVERAGE_PTS,
    )
    RowItem(
      text = poolValueString,
      padding = Constants.UI_PADDING_POOL_VALUE
    )
    RowItem(
      text = goaler.averagePoolValue,
      padding = Constants.UI_PADDING_ID
    )

    goaler.history.forEach {
      RowItem(
        text = it,
        padding = Constants.UI_PADDING_HISTORY,
      )
    }
  }
}