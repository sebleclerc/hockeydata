package ca.sebleclerc.hockeydata.shared.ui.features.pool.taken

import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.PoolViewModel

class PoolTakenViewModel(
  val dbService: DatabaseService,
) : PoolViewModel(dbService) {
  override fun refreshAllPlayersProperty() {
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

    allPlayers = players
      .sortedWith(compareBy { it.player.fullName })
  }
}
