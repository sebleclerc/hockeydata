package ca.sebleclerc.hockeydata.shared.ui.features.pool.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.core.domain.Player
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
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

  var allPlayers = mutableListOf<PoolSkaterPlayer>()
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
    dbService.updatePlayerForPool(
      playerId = player.player.id,
      statut = statut
    )

    if (statut != PoolDraftStatut.WATCH) {
      allPlayers.remove(player)

      refreshView(refreshPlayers = false)
    }
  }

  private fun didUpdateSearch(searchValue: String) {
    searchTerm = searchValue
    refreshView(refreshPlayers = false)
  }

  private fun onClickSortValue(newValue: Boolean) {
    sortPoolValue = newValue
    refreshView(refreshPlayers = false)
  }

  private fun refreshAllPlayers() {
    var players = mutableListOf<PoolSkaterPlayer>()

    val poolPreviewStatuses = dbService.getAllPoolDraftStatuses()
    val dbPlayers = dbService.getAllPlayers(false)

    dbPlayers.forEach { player ->
      val status = poolPreviewStatuses[player.id]
      if (shouldKeepPlayer(player, status)) {
        val seasons = dbService.getLastSeasonsForSkaterId(player.id)
        val salary = dbService.getPlayerSeasonSalary(Constants.currentSeason, player.id)
        val team = dbService.getTeamForId(player.teamId)
        val current = dbService.getSingleSeasonForSkateId(player.id, Constants.currentSeason)

        players.add(PoolSkaterPlayer(player, seasons, salary, team, current))
      }
    }

    allPlayers = players
  }

  protected abstract fun shouldKeepPlayer(player: Player, statut: PoolDraftStatut?): Boolean
  protected abstract fun getPlayerComparator(): Comparator<PoolSkaterPlayer>

  private fun refreshView(refreshPlayers: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      if(refreshPlayers) refreshAllPlayers()

      _state.update {
        it.copy(
          players = allPlayers
            .filter { player ->
              if (searchTerm.isEmpty()) {
                true
              } else {
                player.player.fullName
                  .lowercase()
                  .contains(searchTerm.lowercase())
              }
            }
            .sortedWith(getPlayerComparator())
        )
      }

      Thread.sleep(500)
      updateLoading(isLoading = false)
    }
  }
}