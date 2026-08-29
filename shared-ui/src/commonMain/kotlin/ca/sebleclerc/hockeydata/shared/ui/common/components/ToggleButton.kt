package ca.sebleclerc.hockeydata.shared.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(text: String, onClick: (Boolean) -> Unit) {
  var checked by remember { mutableStateOf(false) }

  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(2.dp),
  ) {
    Text(text = text)
    Switch(
      checked = checked,
      onCheckedChange = {
        checked = it
        onClick(it)
      }
    )
  }
}