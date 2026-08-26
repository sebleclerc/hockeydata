package ca.sebleclerc.hockeydata.shared.ui.teams

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun TeamActionsView(onAction: (TeamsAction) -> Unit) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Button(onClick = { onAction(TeamsAction.Reload) }){
      Text("Reload")
    }

    Button(onClick = { onAction(TeamsAction.RefreshRosters) }){
      Text("Update Rosters")
    }
  }
}