package ca.sebleclerc.hockeydata.shared.ui.features.pool.preview

import ca.sebleclerc.hockeydata.core.domain.Player
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.PoolViewModel
import kotlinx.coroutines.flow.update

class PoolPreviewViewModel(
  val dbService: DatabaseService,
) : PoolViewModel(dbService) {
  override fun shouldKeepPlayer(player: Player, statut: PoolDraftStatut?): Boolean {
    return statut == null || statut == PoolDraftStatut.AVAILABLE || statut == PoolDraftStatut.WATCH
  }

  override fun getPlayerComparator(): Comparator<PoolSkaterPlayer> {
    return compareByDescending {
      if (sortPoolValue) {
        it.poolValue
      } else {
        it.averagePoints
      }
    }
  }

//    players = players
//      .filter { it.averagePoints > -1 }
//      .toMutableList()
//
//    players = if (searchTerm.isEmpty()) {
//      players
//    } else {
//      players.filter {
//        it.player.fullName
//          .lowercase()
//          .contains(searchTerm.lowercase())
//      }
//    }
//      .toMutableList()
//

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
