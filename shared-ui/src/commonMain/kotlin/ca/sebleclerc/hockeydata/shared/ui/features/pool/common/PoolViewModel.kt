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
import kotlinx.coroutines.launch

abstract class PoolViewModel(
  private val dbService: DatabaseService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  val _state = MutableStateFlow(State())
  val state = _state.asStateFlow()

  fun onAction(action: Actions) {
    when (action) {
      is Actions.OnPlayerAction -> onPlayerAction(action.player, action.statut)
    }
  }

  abstract fun refreshPlayersList()

  private fun onPlayerAction(player: PoolSkaterPlayer, statut: PoolDraftStatut) {
    updateLoading(isLoading = true)

    dbService.updatePlayerForPool(
      playerId = player.player.id,
      statut = statut
    )

    viewModelScope.launch(Dispatchers.IO) {
      refreshPlayersList()

      updateLoading(isLoading = false)
    }
  }
}