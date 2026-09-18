package ca.sebleclerc.hockeydata.shared.ui.features.poolme

import ca.sebleclerc.hockeydata.core.domain.PoolMePlayer

sealed interface Actions {
  data object Update : Actions
  data class Available(val player: PoolMePlayer) : Actions
}
