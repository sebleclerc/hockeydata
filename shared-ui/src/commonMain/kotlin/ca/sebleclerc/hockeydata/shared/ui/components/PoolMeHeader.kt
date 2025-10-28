package ca.sebleclerc.hockeydata.shared.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay.RowItem

@Composable
fun PoolMeHeader() {
  Row {
    RowItem(text = "ID", padding = Constants.UI_PADDING_ID)
    RowItem(text = "Name", padding = Constants.UI_PADDING_NAME)
    RowItem(text = "Games", padding = Constants.UI_PADDING_ID)
    RowItem(text = "G", padding = Constants.UI_PADDING_ID)
    RowItem(text = "A", padding = Constants.UI_PADDING_ID)
    RowItem(text = "Pts", padding = Constants.UI_PADDING_ID)
    RowItem(text = "Pool", padding = Constants.UI_PADDING_ID)
    RowItem(text = "Salary", padding = Constants.UI_PADDING_AVV)
  }
}