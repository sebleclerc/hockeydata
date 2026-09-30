package ca.sebleclerc.hockeydata.shared.ui.features.goalers

import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageLayout

@Composable
fun GoalersScreen(
  state: State,
  onAction: (Actions) -> Unit,
) {
  PageLayout(
    title = "Goalers",
    listHeader = { Header() }
  ) {
    items(count = state.players.size) {
      val player = state.players[it]

      Row(
        goaler = player,
        actions = listOf(
          RowAction(
            label = "Watch",
            action = Actions.OnPlayerAction(player, PoolDraftStatut.WATCH)
          ),
          RowAction(
            label = "Taken",
            action = Actions.OnPlayerAction(player, PoolDraftStatut.TAKEN)
          ),
          RowAction(
            label = "ME",
            action = Actions.OnPlayerAction(player, PoolDraftStatut.SELECTED)
          )
        ),
        onAction = onAction
      )
    }
  }
}