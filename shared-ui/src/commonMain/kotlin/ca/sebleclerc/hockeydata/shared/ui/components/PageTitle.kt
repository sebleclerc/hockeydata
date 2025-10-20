package ca.sebleclerc.hockeydata.shared.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PageTitle(title: String) {
  Text(
    text = title,
    textAlign = TextAlign.Center,
    modifier =
      Modifier
        .padding(8.dp)
        .fillMaxWidth(),
  )
}
