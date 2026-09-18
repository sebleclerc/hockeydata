package ca.sebleclerc.hockeydata.shared.ui.features.pool.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.common.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.common.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class PoolViewModel(
  private val dbService: DatabaseService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  val _state = MutableStateFlow(State())
  val state = _state.asStateFlow()

  var allPlayers = emptyList<PoolSkaterPlayer>()
  var searchTerm = ""
  var sortPoolValue = false

  init {
    updateLoading(true)

    refreshView(refreshPlayers = true)
  }

  fun onAction(action: Actions) {
    when (action) {
      is Actions.OnPlayerAction -> onPlayerAction(action.player, action.statut)
      is Actions.DidClickSortValue -> onClickSortValue(action.value)
      is Actions.OnSearchValueChanged -> didUpdateSearch(action.search)
    }
  }

  private fun onPlayerAction(player: PoolSkaterPlayer, statut: PoolDraftStatut) {
    updateLoading(isLoading = true)

    dbService.updatePlayerForPool(
      playerId = player.player.id,
      statut = statut
    )

    refreshView(refreshPlayers = true)
  }

  private fun didUpdateSearch(searchValue: String) {
    searchTerm = searchValue
    refreshView()
  }

  private fun onClickSortValue(newValue: Boolean) {
    updateLoading(isLoading = true)
    sortPoolValue = newValue
    refreshView()
  }

  abstract fun refreshAllPlayersProperty()

  private fun refreshView(refreshPlayers: Boolean = false) {
    viewModelScope.launch(Dispatchers.IO) {
      if(refreshPlayers) refreshAllPlayersProperty()

      _state.update {
        it.copy(
          players = allPlayers,
        )
      }

      Thread.sleep(500)
      updateLoading(isLoading = false)
    }
  }
}