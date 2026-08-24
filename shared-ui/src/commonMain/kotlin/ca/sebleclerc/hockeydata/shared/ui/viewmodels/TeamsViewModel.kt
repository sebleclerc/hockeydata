package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.components.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.components.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamsViewModel(
  val dbService: DatabaseService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  private val _state = MutableStateFlow(TeamsState())
  val state = _state.asStateFlow()

  init {
    updateLoading(isLoading = true)

    viewModelScope.launch(Dispatchers.IO) {
      fetchAllTeams()
    }
  }

  private fun fetchAllTeams() {
    val teams = dbService.getAllTeams()

    _state.update {
      it.copy(
        data = teams.map {
          val roster = dbService.getRosterForTeam(it.id)
          val nbPlayersInRoster = roster.count()
          var nbPlayerInDB = 0

          roster.forEach { playerId ->
            val player = dbService.getPlayerForId(playerId)
            if (player != null) nbPlayerInDB += 1
          }

          val proportion = "$nbPlayerInDB / $nbPlayersInRoster"

          Pair(it, proportion)
        }
      )
    }

    updateLoading(isLoading = false)
  }
}