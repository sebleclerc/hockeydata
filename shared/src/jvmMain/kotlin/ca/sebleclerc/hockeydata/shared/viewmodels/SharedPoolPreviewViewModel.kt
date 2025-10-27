package ca.sebleclerc.hockeydata.shared.viewmodels

import androidx.lifecycle.ViewModel
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.core.helpers.Logger
import ca.sebleclerc.hockeydata.database.DatabaseService

open class SharedPoolPreviewViewModel(
  private val dbService: DatabaseService,
) : ViewModel() {
  fun getAllPoolPreviewPlayers(
    teamId: Int? = null,
    name: String? = null,
    minimal: Boolean,
    current: Boolean? = null,
    sortValue: Boolean,
  ): List<PoolSkaterPlayer> {
    Logger.debug("[SharedPoolPreviewViewModel] getAllPoolPreviewPlayers")
    val players = fetchPoolSkaterPlayerFromDatabase()

    return players
      .filter {
        if (name != null) {
          it.player.fullName.contains(name!!)
        } else if (teamId != null) {
          it.averagePoints > -1
        } else if (minimal) {
          it.averagePoints > 20
        } else {
          it.averagePoints > -1
        }
      }.sortedWith(
        compareBy {
          if (current == true) {
            it.current?.poolPoints?.toDouble()
          } else if (sortValue) {
            it.poolValue
          } else {
            it.averagePoints
          }
        },
      ).reversed()
  }

  private fun fetchPoolSkaterPlayerFromDatabase(teamId: Int? = null): List<PoolSkaterPlayer> {
    val players = mutableListOf<PoolSkaterPlayer>()

    val poolPreviewStatuses = dbService.getAllPoolDraftStatuses()
    val dbPlayers =
      if (teamId != null) {
        dbService.getPlayersForTeam(teamId!!)
      } else {
        dbService.getAllPlayers(false)
      }

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
