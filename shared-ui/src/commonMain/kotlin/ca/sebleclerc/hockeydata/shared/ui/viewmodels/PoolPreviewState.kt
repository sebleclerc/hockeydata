package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer

data class PoolPreviewState(
  val skaterPlayers: List<PoolSkaterPlayer> = emptyList(),
)
