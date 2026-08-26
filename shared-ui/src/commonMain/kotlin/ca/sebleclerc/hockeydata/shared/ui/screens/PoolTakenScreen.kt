package ca.sebleclerc.hockeydata.shared.ui.screens

import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.shared.ui.components.PoolPreviewHeader
import ca.sebleclerc.hockeydata.shared.ui.components.PoolTakenRow
import ca.sebleclerc.hockeydata.shared.ui.components.page.PageLayout
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolTakenState

@Composable
fun PoolTakenScreen(
  state: PoolTakenState,
) {
  PageLayout(
    title = "Pool Taken",
    listHeader = { PoolPreviewHeader() }
  ) {
    items(count = state.allPLayers.size) {
      val player = state.allPLayers[it]
      PoolTakenRow(player)
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
