package ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.sebleclerc.hockeydata.core.helpers.Constants

@Composable
fun RowButton(
  text: String,
  onClick: () -> Unit,
) {
  Button(
    onClick = { onClick() },
    contentPadding = PaddingValues(all = 1.dp),
    modifier =
      Modifier
        .height(Constants.UI_ROW_HEIGHT.dp)
        .padding(horizontal = 5.dp),
  ) {
    Text(
      text = text,
      fontSize = 10.sp,
    )
  }
}
