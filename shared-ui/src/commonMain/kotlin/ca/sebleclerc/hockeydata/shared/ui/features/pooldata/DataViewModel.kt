package ca.sebleclerc.hockeydata.shared.ui.features.pooldata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.cache.CacheService
import ca.sebleclerc.hockeydata.cache.ImportService
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.core.helpers.Logger
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.common.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.common.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class DataViewModel(
  val cacheService: CacheService,
  val dbService: DatabaseService,
  val importService: ImportService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  fun onAction(action: DataActions) {
    updateLoading(isLoading = true)

    when (action) {
      DataActions.PoolDataRefresh -> poolDataRefresh()
      DataActions.CacheTeams -> cacheTeams()
      DataActions.CacheAllPlayers -> cacheAllPlayers()
    }
  }

  private fun cacheTeams() {
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
    viewModelScope.launch(Dispatchers.IO) {
      val players = dbService.getPoolMePlayers()
      val steps = players.map { CacheStep.Player(it.id) }
      cacheService.cache(steps, force = true)
      importService.importPlayers(steps)

      Thread.sleep(500)
      updateLoading(false)
    }
  }

  private fun cacheAllPlayers() {
    viewModelScope.launch(Dispatchers.IO) {
      val players = Path(Constants.JSON_FOLDER).listDirectoryEntries("*-player.json")

      players.forEach {
        val playerId = it.name.split("-")[0].toInt()
        Logger.debug("Caching player $playerId from ${it.name}")

        val step = CacheStep.Player(playerId)

        cacheService.cache(listOf(step), true)
        importService.importPlayers(listOf(step))
      }
    }

    updateLoading(isLoading = false)
  }
}
