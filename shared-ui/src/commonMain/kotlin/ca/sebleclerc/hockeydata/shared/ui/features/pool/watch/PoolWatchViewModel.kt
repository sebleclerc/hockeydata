package ca.sebleclerc.hockeydata.shared.ui.features.pool.watch

import ca.sebleclerc.hockeydata.core.domain.Player
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.PoolViewModel

class PoolWatchViewModel(
  dbService: DatabaseService,
) : PoolViewModel(dbService) {
  override fun shouldKeepPlayer(player: Player, statut: PoolDraftStatut?): Boolean {
    return statut == PoolDraftStatut.WATCH
  }

  override fun getPlayerComparator(): Comparator<PoolSkaterPlayer> {
    return compareBy { it.player.fullName }
  }
}