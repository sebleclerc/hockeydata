package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.cache.CacheService
import ca.sebleclerc.hockeydata.cache.ImportService
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.components.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.components.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamsViewModel(
  val cacheService: CacheService,
  val dbService: DatabaseService,
  val importService: ImportService,
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

  fun onAction(action: TeamsAction) {
    when (action) {
      TeamsAction.RefreshRosters -> {
        updateLoading(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
          updateRosters()
          fetchAllTeams()
        }
      }

      TeamsAction.Reload -> {
        updateLoading(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
          fetchAllTeams()
        }
      }
    }
  }

  private fun fetchAllTeams() {
    val teams = dbService.getAllTeams()
    var totalPlayers = 0
    var totalPlayersSalaries = 0

    val data = teams.map { team ->
      val roster = dbService.getRosterForTeam(team.id)
      val nbPlayersInRoster = roster.count()
      totalPlayers += nbPlayersInRoster

      var nbPlayerInDB = 0
      var nbPlayersSalary = 0

      roster.forEach { playerId ->
        val player = dbService.getPlayerForId(playerId)
        if (player != null) nbPlayerInDB += 1

        val salary = dbService.getPlayerSeasonSalary(Constants.currentSeason, playerId)
        if (salary != null) nbPlayersSalary += 1
      }

      totalPlayersSalaries += nbPlayersSalary

      val dbProportion = "$nbPlayerInDB / $nbPlayersInRoster"
      val salaryProportion = "$nbPlayersSalary / $nbPlayersInRoster"

      Triple(team, dbProportion, salaryProportion)
    }

    _state.update { teamsState ->
      teamsState.copy(
        data = data,
        totalSalaryProportion = "$totalPlayersSalaries / $totalPlayers"
      )
    }

    updateLoading(isLoading = false)
  }

  private fun updateRosters() {
    val teams = dbService
      .getAllTeams()

    val steps: MutableList<CacheStep> = mutableListOf(CacheStep.Teams())
    steps += teams.map { CacheStep.CacheTeamRoster(it) }

    cacheService.cache(
      steps = steps,
      force = false
    )

    importService.importRosters()

    teams.forEach { team ->
      val roster = dbService.getRosterForTeam(team.id)
      val playerSteps = roster.map { CacheStep.Player(it) }
      cacheService.cache(playerSteps, false)
      importService.importPlayers(playerSteps)
    }
  }
}