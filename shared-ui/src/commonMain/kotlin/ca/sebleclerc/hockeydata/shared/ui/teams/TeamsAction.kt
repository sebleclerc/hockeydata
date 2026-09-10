package ca.sebleclerc.hockeydata.shared.ui.teams

sealed interface TeamsAction {
  data object Reload : TeamsAction
  data object RefreshRosters : TeamsAction
  data class CacheTeam(val id: Int) : TeamsAction
}