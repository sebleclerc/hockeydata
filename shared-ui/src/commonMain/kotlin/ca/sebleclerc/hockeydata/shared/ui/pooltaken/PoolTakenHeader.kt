package ca.sebleclerc.hockeydata.shared.ui.pooltaken

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay.Header

@Composable
fun PoolTakenHeader() {
  Row {
    Header(text = "ID", padding = Constants.UI_PADDING_ID)
    Header(text = "Name", padding = Constants.UI_PADDING_NAME)
    Header(text = "Pos", padding = Constants.UI_PADDING_POSITION)
    Header(text = "Team", padding = Constants.UI_PADDING_TEAM_ABBREV)
    Header(text = "Salary", padding = Constants.UI_PADDING_AVV)
    Header(text = "Cur.", padding = Constants.UI_PADDING_ID)
    Header(text = "Average", padding = Constants.UI_PADDING_ID)
  }
}
