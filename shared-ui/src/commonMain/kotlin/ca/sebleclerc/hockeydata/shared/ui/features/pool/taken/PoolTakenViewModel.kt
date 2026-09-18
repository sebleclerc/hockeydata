package ca.sebleclerc.hockeydata.shared.ui.features.pool.taken

import ca.sebleclerc.hockeydata.core.domain.Player
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.PoolViewModel

class PoolTakenViewModel(
  dbService: DatabaseService,
) : PoolViewModel(dbService) {
  override fun shouldKeepPlayer(player: Player, statut: PoolDraftStatut?): Boolean {
    return statut == PoolDraftStatut.TAKEN
  }

  override fun getPlayerComparator(): Comparator<PoolSkaterPlayer> {
    return compareBy { it.player.fullName }
  }
}
