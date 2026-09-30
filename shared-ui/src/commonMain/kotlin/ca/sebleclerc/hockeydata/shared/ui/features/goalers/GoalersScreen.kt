package ca.sebleclerc.hockeydata.shared.ui.features.goalers

import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageLayout

@Composable
fun GoalersScreen(state: State) {
  PageLayout(
    title = "Goalers",
    listHeader = { Header() }
  ) {
    items(count = state.players.size) {
      val player = state.players[it]

      Row(
        goaler = player,
      )
    }
  }
}