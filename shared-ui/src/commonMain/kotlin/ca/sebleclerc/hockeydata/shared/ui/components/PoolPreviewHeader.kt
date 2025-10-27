package ca.sebleclerc.hockeydata.shared.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.core.helpers.Constants

@Composable
fun PoolPreviewHeader() {
  Row {
    LazyColumnHeader(text = "ID", padding = Constants.UI_PADDING_ID)
    LazyColumnHeader(text = "Name", padding = Constants.UI_PADDING_NAME)
    LazyColumnHeader(text = "Pos", padding = Constants.UI_PADDING_POSITION)
    LazyColumnHeader(text = "Team", padding = Constants.UI_PADDING_TEAM_ABBREV)
    LazyColumnHeader(text = "Salary", padding = Constants.UI_PADDING_AVV)
    LazyColumnHeader(text = "Cur.", padding = Constants.UI_PADDING_ID)
    LazyColumnHeader(text = "Average", padding = Constants.UI_PADDING_ID)
    LazyColumnHeader(text = "V. Last", padding = Constants.UI_PADDING_ID)
    LazyColumnHeader(text = "V. Avg.", padding = Constants.UI_PADDING_ID)
    LazyColumnHeader(text = "History", padding = Constants.UI_PADDING_HISTORY)
  }
}
