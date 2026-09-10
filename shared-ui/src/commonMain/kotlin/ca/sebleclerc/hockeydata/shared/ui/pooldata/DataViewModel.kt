package ca.sebleclerc.hockeydata.shared.ui.pooldata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.cache.CacheService
import ca.sebleclerc.hockeydata.cache.ImportService
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.common.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.common.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(
  val cacheService: CacheService,
  val dbService: DatabaseService,
  val importService: ImportService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  fun onAction(action: DataActions) {
    when (action) {
      DataActions.PoolDataRefresh -> poolDataRefresh()
      DataActions.CacheTeams -> cacheTeams()
    }
  }

  private fun cacheTeams() {
    updateLoading(isLoading = true)

    viewModelScope.launch(Dispatchers.IO) {
      val teams = dbService.getAllTeams()
      val steps = teams.map { CacheStep.CacheTeamRoster(it) }
      cacheService.cache(steps, true, showProgress = true)
      importService.importRosters()

      teams.forEach { team ->
        val roster = dbService.getRosterForTeam(team.id)
        val playerSteps = roster.map { CacheStep.Player(it) }
        cacheService.cache(playerSteps, false)
        importService.importPlayers(playerSteps)
      }

      updateLoading(isLoading = false)
    }
  }

  private fun poolDataRefresh() {
    updateLoading(true)

    viewModelScope.launch(Dispatchers.IO) {
      val players = dbService.getPoolMePlayers()
      val steps = players.map { CacheStep.Player(it.id) }
      cacheService.cache(steps, force = true)
      importService.importPlayers(steps)

      Thread.sleep(500)
      updateLoading(false)
    }
  }
}
