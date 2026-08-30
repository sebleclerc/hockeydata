package ca.sebleclerc.hockeydata.shared.ui.poolpreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.core.helpers.Logger
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.common.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.common.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PoolPreviewViewModel(
  val dbService: DatabaseService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  private val _state = MutableStateFlow(PoolPreviewState())
  val state = _state.asStateFlow()

  private var allPlayers = emptyList<PoolSkaterPlayer>()
  private var searchTerm = ""
  private var sortPoolValue = false

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
      is PoolPreviewAction.DidClickSortValue -> onClickSortValue(action.value)
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
    searchTerm = searchValue

    viewModelScope.launch(Dispatchers.IO) {
      updateState()
    }
  }

  private fun onClickSortValue(newValue: Boolean) {
    sortPoolValue = newValue

    viewModelScope.launch(Dispatchers.IO) {
      updateState()
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

  private fun updateState(
    refreshPlayers: Boolean = false,
  ) {
      if (refreshPlayers) {
        allPlayers = fetchPoolSkaterPlayerFromDatabase()
      }

    var players =
      if (searchTerm.isEmpty()) {
        allPlayers
      } else {
        allPlayers.filter {
          it.player.fullName
            .lowercase()
            .contains(searchTerm.lowercase())
        }
      }

    players = players.sortedWith(
      compareBy {
        if (sortPoolValue) {
          it.poolValue
        } else {
          it.averagePoints
        }
      }
    ).reversed()

    _state.update {
      it.copy(
        filteredPlayers = players,
      )
    }

    Thread.sleep(500)
    updateLoading(false)
  }
  
  private fun fetchPoolSkaterPlayerFromDatabase(): List<PoolSkaterPlayer> {
    val players = mutableListOf<PoolSkaterPlayer>()

    val poolPreviewStatuses = dbService.getAllPoolDraftStatuses()
    val dbPlayers = dbService.getAllPlayers(false)

    dbPlayers.forEach { player ->
      val status = poolPreviewStatuses[player.id]
      if (status == null || status == PoolDraftStatut.AVAILABLE) {
        val seasons = dbService.getLastSeasonsForSkaterId(player.id)
        val salary = dbService.getPlayerSeasonSalary(Constants.currentSeason, player.id)
        val team = dbService.getTeamForId(player.teamId)
        val current = dbService.getSingleSeasonForSkateId(player.id, Constants.currentSeason)

        players.add(PoolSkaterPlayer(player, seasons, salary, team, current))
      }
    }

    return players
      .filter { it.averagePoints > -1 }
  }

  // endregion
}
