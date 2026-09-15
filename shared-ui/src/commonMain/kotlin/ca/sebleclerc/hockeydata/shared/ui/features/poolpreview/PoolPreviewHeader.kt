package ca.sebleclerc.hockeydata.shared.ui.features.poolpreview

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay.Header

@Composable
fun PoolPreviewHeader() {
  Row {
    Header(text = "ID", padding = Constants.UI_PADDING_ID)
    Header(text = "Name", padding = Constants.UI_PADDING_NAME)
    Header(text = "Pos", padding = Constants.UI_PADDING_POSITION)
    Header(text = "Team", padding = Constants.UI_PADDING_TEAM_ABBREV)
    Header(text = "Salary", padding = Constants.UI_PADDING_AVV)
    Header(text = "Pts", padding = Constants.UI_PADDING_CURRENT)
    Header(text = "Av Pts", padding = Constants.UI_PADDING_AVERAGE_PTS)
    Header(text = "Value", padding = Constants.UI_PADDING_POOL_VALUE)
    Header(text = "Val Avg.", padding = Constants.UI_PADDING_ID)
    Header(text = "History", padding = Constants.UI_PADDING_HISTORY)
  }
}
