package ca.sebleclerc.hockeydata.shared.ui.features.teams

sealed interface TeamsAction {
  data object Reload : TeamsAction
  data object RefreshRosters : TeamsAction
  data class CacheTeam(val id: Int) : TeamsAction
}