package ca.sebleclerc.hockeydata.shared.ui.features.pooltaken

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

sealed interface PoolTakenActions {
  data class OnPlayerAvailable(
    val player: PoolSkaterPlayer,
  ) : PoolTakenActions
}