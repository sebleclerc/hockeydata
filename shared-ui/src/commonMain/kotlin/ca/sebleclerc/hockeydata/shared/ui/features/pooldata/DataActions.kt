package ca.sebleclerc.hockeydata.shared.ui.features.pooldata

sealed interface DataActions {
  data object CacheTeams : DataActions
  data object PoolDataRefresh : DataActions
}
