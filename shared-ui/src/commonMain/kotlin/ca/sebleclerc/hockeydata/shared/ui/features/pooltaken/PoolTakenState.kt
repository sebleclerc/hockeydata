package ca.sebleclerc.hockeydata.shared.ui.features.pooltaken

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

data class PoolTakenState(
  val allPLayers: List<PoolSkaterPlayer> = emptyList(),
)
