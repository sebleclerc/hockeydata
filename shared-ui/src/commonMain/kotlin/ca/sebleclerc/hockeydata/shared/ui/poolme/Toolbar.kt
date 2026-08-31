package ca.sebleclerc.hockeydata.shared.ui.poolme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun Toolbar(onClick: (Actions) -> Unit) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    Button(onClick = { onClick(Actions.Update) }) {
      Text("Update")
    }
  }
}