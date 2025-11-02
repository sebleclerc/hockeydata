package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

sealed interface PoolPreviewAction {
  data class OnPlayerTaken(
    val player: PoolSkaterPlayer,
  ) : PoolPreviewAction

  data class OnPlayerSelect(
    val player: PoolSkaterPlayer,
  ) : PoolPreviewAction

  data class OnSearchValueChanged(
    val search: String,
  ) : PoolPreviewAction
}
