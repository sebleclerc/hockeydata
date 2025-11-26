package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Logger
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.components.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.components.loading.LoadingViewModel
import ca.sebleclerc.hockeydata.shared.viewmodels.SharedPoolPreviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PoolPreviewViewModel(
  dbService: DatabaseService,
) : SharedPoolPreviewViewModel(dbService), Loading by LoadingViewModel() {
  private val _state = MutableStateFlow(PoolPreviewState())
  val state = _state.asStateFlow()

  init {
    updateLoading(true)

    viewModelScope.launch(Dispatchers.IO) {
      updateState(refreshPlayers = true)
    }
  }

  // region Public

  fun onAction(action: PoolPreviewAction) {
    Logger.debug("PPVM onAction $action")

    when (action) {
      is PoolPreviewAction.OnPlayerSelect -> didReceivedOnPlayerSelect(player = action.player)
      is PoolPreviewAction.OnPlayerTaken -> didReceivedOnPlayerTaken(player = action.player)
      is PoolPreviewAction.OnSearchValueChanged -> didUpdateSearch(action.search)
    }
  }

  // endregion

  // region Actions

  private fun didReceivedOnPlayerSelect(player: PoolSkaterPlayer) {
    updatePlayerAndRefresh(
      playerId = player.player.id,
      statut = PoolDraftStatut.SELECTED,
    )
  }

  private fun didReceivedOnPlayerTaken(player: PoolSkaterPlayer) {
    updatePlayerAndRefresh(
      playerId = player.player.id,
      statut = PoolDraftStatut.TAKEN,
    )
  }

  private fun didUpdateSearch(searchValue: String) {
    viewModelScope.launch(Dispatchers.IO) {
      updateState(newSearch = searchValue)
    }
  }

  // endregion

  // region Helpers

  private fun updatePlayerAndRefresh(
    playerId: Int,
    statut: PoolDraftStatut,
  ) {
    updateLoading(true)

    dbService.updatePlayerForPool(
      playerId = playerId,
      statut = statut,
    )

    viewModelScope.launch(Dispatchers.IO) {
      updateState(refreshPlayers = true)
    }
  }

  private suspend fun updateState(
    refreshPlayers: Boolean = false,
    newSearch: String? = null,
  ) {
    val allPlayers =
      if (refreshPlayers) {
        getAllPoolPreviewPlayers(
          minimal = false,
          sortValue = false,
        )
      } else {
        _state.value.allPLayers
      }

    val searchTerm = newSearch ?: _state.value.currentSearchValue

    val filtered =
      if (searchTerm.isEmpty()) {
        allPlayers
      } else {
        allPlayers.filter {
          it.player.fullName
            .lowercase()
            .contains(searchTerm.lowercase())
        }
      }

    _state.update {
      it.copy(
        allPLayers = allPlayers,
        filteredPlayers = filtered,
        currentSearchValue = searchTerm,
      )
    }

    Thread.sleep(500)
    updateLoading(false)
  }

  // endregion
}
