package ca.sebleclerc.hockeydata.shared.ui.poolpreview

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

data class PoolPreviewState(
  val filteredPlayers: List<PoolSkaterPlayer> = emptyList(),
)
