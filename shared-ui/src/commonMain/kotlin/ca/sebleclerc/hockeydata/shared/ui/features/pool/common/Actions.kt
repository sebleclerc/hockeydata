package ca.sebleclerc.hockeydata.shared.ui.features.pool.common

import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

sealed interface Actions {
  data class OnPlayerAction(
    val player: PoolSkaterPlayer,
    val statut: PoolDraftStatut
  ) : Actions
}