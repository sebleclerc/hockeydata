package ca.sebleclerc.hockeydata.shared.ui.poolme

import ca.sebleclerc.hockeydata.core.domain.PoolMePlayer

data class PoolMeState(
  val forwards: List<PoolMePlayer> = emptyList(),
  val defenses: List<PoolMePlayer> = emptyList(),
  val salary: Int = 0,
  val poolPoints: Float = 0F,
)
