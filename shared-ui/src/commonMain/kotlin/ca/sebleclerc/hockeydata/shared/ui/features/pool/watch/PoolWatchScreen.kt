package ca.sebleclerc.hockeydata.shared.ui.features.pool.watch

import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageLayout
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.Actions
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.Header
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.Row
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.RowAction
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.State

@Composable
fun PoolWatchScreen(
  state: State,
  onAction: (Actions) -> Unit
) {
  PageLayout(
    title = "Pool Watch",
    listHeader = { Header() }
  ) {
    items(count = state.players.size) {
      val player = state.players[it]

      Row(
        player = player,
        actions = listOf(
          RowAction(
            label = "Avail",
            action = Actions.OnPlayerAction(player, PoolDraftStatut.AVAILABLE)
          ),
          RowAction(
            label = "Taken",
            action = Actions.OnPlayerAction(player, PoolDraftStatut.TAKEN)
          ),
          RowAction(
            label = "ME",
            action = Actions.OnPlayerAction(player, PoolDraftStatut.SELECTED)
          ),
        ),
        onAction = onAction
      )
    }
  }
}