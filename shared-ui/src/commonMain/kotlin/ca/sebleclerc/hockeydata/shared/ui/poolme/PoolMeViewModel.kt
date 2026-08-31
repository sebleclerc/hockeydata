package ca.sebleclerc.hockeydata.shared.ui.poolme

import androidx.lifecycle.ViewModel
import ca.sebleclerc.hockeydata.cache.CacheService
import ca.sebleclerc.hockeydata.cache.ImportService
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.core.domain.PoolMePlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.common.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.common.loading.LoadingViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PoolMeViewModel(
  val cacheService: CacheService,
  val dbService: DatabaseService,
  val importService: ImportService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  private val _state = MutableStateFlow(PoolMeState())
  val state = _state.asStateFlow()

  init {
    loadData()
  }

  fun onAction(action: Actions) {
    when (action) {
      is Actions.Update -> updateData()
    }
  }

  private fun updateData() {
    updateLoading(isLoading = true)
    cachePoolMeData()
    loadData()
    updateLoading(isLoading = false)
  }

  private fun loadData() {
    val players = mutableListOf<PoolMePlayer>()
    var totalSalary = 0
    var totalPoolPoints = 0.0F

    val mePlayers = dbService.getPoolMePlayers()
    mePlayers.forEach {
      val stats = dbService.getSingleSeasonForSkateId(it.id, Constants.currentSeason)
      totalPoolPoints += stats?.poolPoints ?: 0F

      val salary = dbService.getPlayerSeasonSalary(Constants.currentSeason, it.id)
      totalSalary += salary?.salary ?: 0

      players.add(
        PoolMePlayer(
          player = it,
          salary = salary,
          stats = stats,
        ),
      )
    }

    val forwards = players.filter { it.player.positionCode != "D" }
    val defenses = players.filter { it.player.positionCode == "D" }

    _state.update {
      it.copy(
        forwards = forwards,
        defenses = defenses,
        salary = totalSalary,
        poolPoints = totalPoolPoints,
      )
    }
  }

  private fun cachePoolMeData() {
    val players = dbService.getPoolMePlayers()
    val steps = players.map { CacheStep.Player(it.id) }
    cacheService.cache(steps, force = true)
    importService.importPlayers(steps)
  }
}
