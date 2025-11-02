package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

data class PoolPreviewState(
  val allPLayers: List<PoolSkaterPlayer> = emptyList(),
  val filteredPlayers: List<PoolSkaterPlayer> = emptyList(),
  val currentSearchValue: String = "",
)
