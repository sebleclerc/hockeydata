package ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.helpers.Constants

@Composable
fun EmptyRow() {
  Row(
    modifier = Modifier
      .height(Constants.UI_ROW_HEIGHT.dp)
  ) {}
}