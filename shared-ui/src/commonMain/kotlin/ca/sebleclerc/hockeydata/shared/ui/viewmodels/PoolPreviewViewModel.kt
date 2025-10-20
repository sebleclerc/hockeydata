package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import androidx.lifecycle.ViewModel
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.database.DatabaseService

class PoolPreviewViewModel(
  private val dbService: DatabaseService
) : ViewModel() {
  val previewSkaters: List<PoolSkaterPlayer> = computeSkaters()

  private fun computeSkaters(): List<PoolSkaterPlayer> {
    val players = mutableListOf<PoolSkaterPlayer>()

    val poolPreviewStatuses = dbService.getAllPoolDraftStatuses()
    val dbPlayers = dbService.getAllPlayers()

    dbPlayers.forEach { player ->
      val status = poolPreviewStatuses[player.id]
      if (status == null || status == PoolDraftStatut.AVAILABLE) {
        val seasons = dbService.getLastSeasonsForSkaterId(player.id)
        val salary = dbService.getPlayerSeasonSalary(Constants.currentSeason, player.id)
        val team = dbService.getTeamForId(player.teamId)
        val current = dbService.getSingleSeasonForSkateId(player.id, Constants.currentSeason)
  //        val current = if (current == true) di.database.getSingleSeasonForSkateId(player.id, Constants.currentSeason) else null

        players.add(PoolSkaterPlayer(player, seasons, salary, team, current))
      }
    }

    return players
  }
}
