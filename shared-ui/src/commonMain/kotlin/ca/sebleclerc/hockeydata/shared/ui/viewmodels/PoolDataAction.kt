package ca.sebleclerc.hockeydata.shared.ui.viewmodels

sealed interface PoolDataAction {
  data object PoolDataRefresh : PoolDataAction
}