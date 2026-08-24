package ca.sebleclerc.hockeydata.shared.ui.viewmodels

sealed interface TeamsAction {
  data object RefreshRosters : TeamsAction
}