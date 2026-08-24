package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import ca.sebleclerc.hockeydata.core.domain.Team

data class TeamsState(
  val teams: List<Team> = emptyList(),
)