package ca.sebleclerc.hockeydata.shared.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.core.helpers.Formatter

@Composable
fun PoolPreviewRow(player: PoolSkaterPlayer) {
  Row {
    LazyColumnRow(text = player.player.id.toString(), padding = Constants.UI_PADDING_ID)
    LazyColumnRow(text = player.player.fullName, padding = Constants.UI_PADDING_NAME)
    LazyColumnRow(text = player.player.positionCode, padding = Constants.UI_PADDING_POSITION)
    LazyColumnRow(text = player.team?.abbreviation ?: "N/A", padding = Constants.UI_PADDING_TEAM_ABBREV)
    LazyColumnRow(text = player.salary?.avv ?: "N/A", padding = Constants.UI_PADDING_AVV)
    LazyColumnRow(text = (player.current?.poolPoints ?: 0F).toString(), padding = Constants.UI_PADDING_ID)
    LazyColumnRow(text = Formatter.roundDouble(player.averagePoints), padding = Constants.UI_PADDING_ID)
    LazyColumnRow(text = player.poolValue, padding = Constants.UI_PADDING_ID)
    LazyColumnRow(text = player.averagePoolValue, padding = Constants.UI_PADDING_ID)
    player.history.forEach {
      LazyColumnRow(
        text = it, padding = 100
      )
    }
  }
}