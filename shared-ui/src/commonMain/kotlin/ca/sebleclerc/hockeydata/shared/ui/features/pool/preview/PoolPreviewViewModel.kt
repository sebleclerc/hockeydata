package ca.sebleclerc.hockeydata.shared.ui.features.pool.preview

import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.PoolViewModel
import kotlinx.coroutines.flow.update

class PoolPreviewViewModel(
  val dbService: DatabaseService,
) : PoolViewModel(dbService) {
  override fun refreshAllPlayersProperty() {
    var players = mutableListOf<PoolSkaterPlayer>()

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

    players = players
      .filter { it.averagePoints > -1 }
      .toMutableList()

    players = if (searchTerm.isEmpty()) {
      players
    } else {
      players.filter {
        it.player.fullName
          .lowercase()
          .contains(searchTerm.lowercase())
      }
    }
      .toMutableList()

    players = players.sortedWith(
      compareBy {
        if (sortPoolValue) {
          it.poolValue
        } else {
          it.averagePoints
        }
      }
    ).reversed()
      .toMutableList()


    allPlayers = players
  }

  // region Helpers

  private fun updateState(
    refreshPlayers: Boolean = false,
  ) {
      if (refreshPlayers) {
//        allPlayers = fetchPoolSkaterPlayerFromDatabase()
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
        players = players,
      )
    }

    Thread.sleep(500)
    updateLoading(false)
  }

  // endregion
}
