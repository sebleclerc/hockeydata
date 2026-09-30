package ca.sebleclerc.hockeydata.shared.ui.features.goalers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.core.domain.Player
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolGoalerPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.common.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.common.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GoalersViewModel(val dbService: DatabaseService) : ViewModel(), Loading by LoadingViewModel() {
  private val _state = MutableStateFlow(State())
  val state = _state.asStateFlow()

  var allPlayers = mutableListOf<PoolGoalerPlayer>()
  var searchTerm = ""
  var sortPoolValue = false

  init {
    updateLoading(isLoading = true)

    refreshView(refreshPlayers = true)
  }

  fun onAction(action: Actions) {
    when (action) {
      is Actions.DidClickSortValue -> TODO()
      is Actions.OnPlayerAction -> onGoalerAction(action.player, action.statut)
      is Actions.OnSearchValueChanged -> TODO()
    }
  }

  private fun onGoalerAction(goaler: PoolGoalerPlayer, statut: PoolDraftStatut) {
    dbService.updatePlayerForPool(
      playerId = goaler.player.id,
      statut = statut
    )

    if (statut != PoolDraftStatut.WATCH) {
      allPlayers.remove(goaler)
      refreshView(refreshPlayers = false)
    }
  }

  protected fun shouldKeepPlayer(player: Player, statut: PoolDraftStatut?): Boolean {
    return statut == null || statut == PoolDraftStatut.AVAILABLE || statut == PoolDraftStatut.WATCH
  }

  protected fun getPlayerComparator(): Comparator<PoolGoalerPlayer> {
    return compareByDescending {
      if (sortPoolValue) {
        it.poolValue
      } else {
        it.averagePoints
      }
    }
  }

  private fun refreshView(refreshPlayers: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      if (refreshPlayers) refreshAllPlayers()

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
            .sortedWith(comparator = getPlayerComparator())
        )
      }

      Thread.sleep(200)
      updateLoading(isLoading = false)
    }
  }

  private fun refreshAllPlayers() {
    val players = mutableListOf<PoolGoalerPlayer>()

    val previewStatuses = dbService.getAllPoolDraftStatuses()
    val dbPlayers = dbService.getAllPlayers(onlyGoalers = true)

    dbPlayers.forEach { player ->
      val status = previewStatuses[player.id]

      if (shouldKeepPlayer(player, status)) {
        val seasons = dbService.getLastSeasonsForGoaler(player.id)
        val salary = dbService.getPlayerSeasonSalary(Constants.currentSeason, player.id)
        val team = dbService.getTeamForId(player.teamId)
        val current = dbService.getSingleGoalerSeasonForId(player.id, Constants.currentSeason)

        players.add(
          PoolGoalerPlayer(
            player = player,
            salary = salary,
            team = team,
            seasons = seasons,
            current = current
          )
        )
      }
    }

    allPlayers = players
  }
}