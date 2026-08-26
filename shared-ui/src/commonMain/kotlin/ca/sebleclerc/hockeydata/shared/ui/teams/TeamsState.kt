package ca.sebleclerc.hockeydata.shared.ui.teams

import ca.sebleclerc.hockeydata.core.domain.Team

data class TeamsState(
  val data: List<Triple<Team, String, String>> = emptyList(),
  val totalSalaryProportion: String? = null
)