package ca.sebleclerc.hockeydata.shared.ui.pooltaken

import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageLayout

@Composable
fun PoolTakenScreen(
  state: PoolTakenState,
  onAction: (PoolTakenActions) -> Unit,
) {
  PageLayout(
    title = "Pool Taken",
    listHeader = { PoolTakenHeader() }
  ) {
    items(count = state.allPLayers.size) {
      val player = state.allPLayers[it]
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
