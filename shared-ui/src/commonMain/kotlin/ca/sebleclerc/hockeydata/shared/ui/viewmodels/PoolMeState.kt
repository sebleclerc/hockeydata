package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import ca.sebleclerc.hockeydata.core.domain.PoolMePlayer

data class PoolMeState(
  val forwards: List<PoolMePlayer> = emptyList(),
  val defenses: List<PoolMePlayer> = emptyList(),
  val salary: Int = 0,
)
