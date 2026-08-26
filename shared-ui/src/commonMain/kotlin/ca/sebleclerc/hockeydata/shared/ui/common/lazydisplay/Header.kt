package ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Header(
  text: String,
  padding: Int,
) {
  Text(
    text = text,
    textAlign = TextAlign.Center,
    modifier =
      Modifier
        .width(padding.dp),
  )
}
