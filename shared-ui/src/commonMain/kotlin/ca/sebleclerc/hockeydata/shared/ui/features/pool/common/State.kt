package ca.sebleclerc.hockeydata.shared.ui.features.pool.common

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

data class State(
  val players: List<PoolSkaterPlayer> = emptyList(),
)
