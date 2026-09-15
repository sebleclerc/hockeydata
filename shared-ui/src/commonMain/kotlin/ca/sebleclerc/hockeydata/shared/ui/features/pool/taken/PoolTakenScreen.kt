package ca.sebleclerc.hockeydata.shared.ui.features.pool.taken

import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageLayout
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.Actions
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.Header
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.State

@Composable
fun PoolTakenScreen(
  state: State,
  onAction: (Actions) -> Unit,
) {
  PageLayout(
    title = "Pool Taken",
    listHeader = { Header() }
  ) {
    items(count = state.players.size) {
      val player = state.players[it]
      PoolTakenRow(player, onAction)
    }
  }
}

/*
Nécessaire d'avoir le search field?
TextField(
      value = state.currentSearchValue,
      onValueChange = { onAction(PoolPreviewAction.OnSearchValueChanged(it)) },
      modifier =
        Modifier
          .fillMaxWidth()
          .height(50.dp)
          .padding(end = 10.dp),
    )
 */
