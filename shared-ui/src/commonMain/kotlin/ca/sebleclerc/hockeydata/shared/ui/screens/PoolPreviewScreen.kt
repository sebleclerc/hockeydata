package ca.sebleclerc.hockeydata.shared.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ca.sebleclerc.hockeydata.shared.ui.components.PoolPreviewHeader
import ca.sebleclerc.hockeydata.shared.ui.components.PoolPreviewRow
import ca.sebleclerc.hockeydata.shared.ui.components.page.PageTitle
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolPreviewAction
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolPreviewState

@Composable
fun PoolPreviewScreen(
  state: PoolPreviewState,
  onAction: (PoolPreviewAction) -> Unit,
) {
  Column {
    PageTitle("Pool Preview")
    PoolPreviewHeader()
    LazyColumn(
      modifier =
        Modifier
          .fillMaxWidth()
    ) {
      items(count = state.skaterPlayers.size) {
        val player = state.skaterPlayers[it]
        PoolPreviewRow(player, onAction)
      }
    }
  }
}
