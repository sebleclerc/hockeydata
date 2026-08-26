package ca.sebleclerc.hockeydata.shared.ui.pooldata

sealed interface PoolDataAction {
  data object PoolDataRefresh : PoolDataAction
}
