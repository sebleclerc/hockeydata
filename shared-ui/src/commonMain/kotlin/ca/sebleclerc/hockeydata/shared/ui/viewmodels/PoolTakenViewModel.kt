package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.components.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.components.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PoolTakenViewModel(
  val dbService: DatabaseService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  private val _state = MutableStateFlow(PoolTakenState())
  val state = _state.asStateFlow()

  init {
    updateLoading(true)

    viewModelScope.launch(Dispatchers.IO) {
      fetchPoolSkaterPlayerFromDatabase()
    }
  }

  private fun fetchPoolSkaterPlayerFromDatabase() {
    val players = mutableListOf<PoolSkaterPlayer>()

    val poolPreviewStatuses = dbService.getAllPoolDraftStatuses()
    val dbPlayers = dbService.getAllPlayers(false)

    dbPlayers.forEach { player ->
      val status = poolPreviewStatuses[player.id]
      if (status == PoolDraftStatut.TAKEN) {
        val seasons = dbService.getLastSeasonsForSkaterId(player.id)
        val salary = dbService.getPlayerSeasonSalary(Constants.currentSeason, player.id)
        val team = dbService.getTeamForId(player.teamId)
        val current = dbService.getSingleSeasonForSkateId(player.id, Constants.currentSeason)

        players.add(PoolSkaterPlayer(player, seasons, salary, team, current))
      }
    }

    _state.update {
      it.copy(
        allPLayers = players.sortedWith(compareBy { it.player.fullName }),
      )
    }

    Thread.sleep(500)
    updateLoading(false)
  }
}
