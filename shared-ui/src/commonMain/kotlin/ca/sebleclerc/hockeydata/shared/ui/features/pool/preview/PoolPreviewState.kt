package ca.sebleclerc.hockeydata.shared.ui.features.pool.preview

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

data class PoolPreviewState(
  val filteredPlayers: List<PoolSkaterPlayer> = emptyList(),
)
