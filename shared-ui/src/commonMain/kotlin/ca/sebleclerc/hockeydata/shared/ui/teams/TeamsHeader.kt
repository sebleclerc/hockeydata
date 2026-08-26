package ca.sebleclerc.hockeydata.shared.ui.teams

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay.Header

@Composable
fun
  TeamsHeader() {
  Row {
    Header(text = "ID", padding = Constants.UI_PADDING_ID)
    Header(text = "Name", padding = Constants.UI_PADDING_NAME)
    Header(text = "DB %", padding = Constants.UI_PADDING_PROPORTION)
    Header(text = "Sal %", padding = Constants.UI_PADDING_PROPORTION)
  }
}