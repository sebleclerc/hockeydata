package ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.sebleclerc.hockeydata.core.helpers.Constants

@Composable
fun RowItem(
  text: String,
  padding: Int,
) {
  Text(
    text = text,
    textAlign = TextAlign.Right,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    modifier =
      Modifier
        .width(padding.dp)
        .height(Constants.UI_ROW_HEIGHT.dp)
        .fillMaxWidth(),
  )
}
