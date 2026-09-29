package ca.sebleclerc.hockeydata.shared.ui.features.goalers

import ca.sebleclerc.hockeydata.core.domain.PoolGoalerPlayer

data class State(
  val players: List<PoolGoalerPlayer> = emptyList(),
)