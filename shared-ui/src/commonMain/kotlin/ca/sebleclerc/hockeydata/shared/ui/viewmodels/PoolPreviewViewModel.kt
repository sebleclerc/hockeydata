package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Logger
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.viewmodels.SharedPoolPreviewViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PoolPreviewViewModel(
  dbService: DatabaseService,
) : SharedPoolPreviewViewModel(dbService) {
  private val _state = MutableStateFlow(PoolPreviewState())
  val state = _state.asStateFlow()

  init {
    refresh()
  }

  fun onAction(action: PoolPreviewAction) {
    Logger.debug("PPVM onAction $action")

    when (action) {
      is PoolPreviewAction.OnPlayerSelect -> {
        didReceivedOnPlayerSelect(player = action.player)
      }
      is PoolPreviewAction.OnPlayerTaken -> {
        didReceivedOnPlayerTaken(player = action.player)
      }
    }
  }

  private fun didReceivedOnPlayerSelect(player: PoolSkaterPlayer) {
    updatePlayerForPool(
      playerId = player.player.id,
      statut = PoolDraftStatut.SELECTED
    )

    refresh()
  }

  private fun didReceivedOnPlayerTaken(player: PoolSkaterPlayer) {
    updatePlayerForPool(
      playerId = player.player.id,
      statut = PoolDraftStatut.TAKEN
    )

    refresh()
  }

  private fun updatePlayerForPool(
    playerId: Int,
    statut: PoolDraftStatut
  ) {
    dbService.updatePlayerForPool(
      playerId = playerId,
      statut = statut
    )
  }

  private fun refresh() {
    val skaters =
      getAllPoolPreviewPlayers(
        minimal = false,
        sortValue = false,
      )

    _state.update { it.copy(skaterPlayers = skaters) }
  }
}
