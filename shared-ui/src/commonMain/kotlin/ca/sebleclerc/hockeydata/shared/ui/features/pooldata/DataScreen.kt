package ca.sebleclerc.hockeydata.shared.ui.features.pooldata

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageTitle

@Composable
fun DataScreen(viewModel: DataViewModel) {
  Column {
    PageTitle("Pool Data")

    Column {
      Button(
        onClick = { viewModel.onAction(DataActions.CacheTeams)}
      ) {
        Text(text = "Cache all teams")
      }
      Text("Cache all teams rosters and all missing players.")

      Button(
        onClick = { viewModel.onAction(DataActions.PoolDataRefresh) },
      ) {
        Text("Update Pool Data")
      }
      Text("Update all players from within MY pool.")
    }

    Button(
      onClick = { viewModel.onAction(DataActions.CacheAllPlayers) }
    ) {
      Text(text = "Cache ALL players (Force)")
    }
    Text("Goes through all players in DB and force cache and import.")
  }
}
