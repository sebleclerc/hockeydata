package ca.sebleclerc.hockeydata.shared.ui.poolpreview

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

data class PoolPreviewState(
  val allPLayers: List<PoolSkaterPlayer> = emptyList(),
  val filteredPlayers: List<PoolSkaterPlayer> = emptyList(),

  val sortByPoolValue: Boolean = false,
)
