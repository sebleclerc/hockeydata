package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import androidx.lifecycle.ViewModel
import ca.sebleclerc.hockeydata.core.domain.PoolMePlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.database.DatabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PoolMeViewModel(val dbService: DatabaseService) : ViewModel() {
  private val _state = MutableStateFlow(PoolMeState())
  val state = _state.asStateFlow()

  init {
    refresh()
  }

  private fun refresh() {
    val players = mutableListOf<PoolMePlayer>()
    var totalSalary = 0

    val mePlayers = dbService.getPoolMePlayers()
    mePlayers.forEach {
      val stats = dbService.getSingleSeasonForSkateId(it.id, Constants.currentSeason)
      val salary = dbService.getPlayerSeasonSalary(Constants.currentSeason, it.id)
      totalSalary += salary?.salary ?: 0

      players.add(
        PoolMePlayer(
          player = it,
          salary = salary,
          stats = stats
        )
      )
    }

    val forwards = players.filter { it.player.positionCode != "D" }
    val defenses = players.filter { it.player.positionCode == "D" }

    _state.update { it.copy(
      forwards = forwards,
      defenses = defenses,
      salary = totalSalary
    ) }
  }
}