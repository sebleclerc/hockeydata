package ca.sebleclerc.hockeydata.shared.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    TextField(
      value = state.currentSearchValue,
      onValueChange = { onAction(PoolPreviewAction.OnSearchValueChanged(it)) },
      modifier =
        Modifier
          .fillMaxWidth()
          .height(50.dp)
          .padding(end = 10.dp),
    )

    PoolPreviewHeader()
    LazyColumn(
      modifier =
        Modifier
          .fillMaxWidth(),
    ) {
      items(count = state.filteredPlayers.size) {
        val player = state.filteredPlayers[it]
        PoolPreviewRow(player, onAction)
      }
    }
  }
}
