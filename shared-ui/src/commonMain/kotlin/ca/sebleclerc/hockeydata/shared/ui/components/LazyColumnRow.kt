package ca.sebleclerc.hockeydata.shared.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LazyColumnRow(text: String, padding: Int) {
  Text(text = text,
  textAlign = TextAlign.Right,
    modifier = Modifier
    .width(padding.dp)
    .fillMaxWidth()
  )
}
