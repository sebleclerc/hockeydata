package ca.sebleclerc.hockeydata.core.helpers

import ca.sebleclerc.hockeydata.core.domain.Season

object Constants {
  val currentSeason = Season(20252026)

  const val DB_URL = "jdbc:mariadb://127.0.0.1:3306/hockeydata"
  const val DB_USER = "sleclerc"
  const val DB_PWD = "sleclerc"

  // Export
  const val jsonFolder = "/Users/sleclerc/Developer/hockeydata/json"

  // UI
  const val paddingAVV = 15
  const val paddingFloat = 10
  const val paddingId = 8
  const val paddingInt = 5
  const val paddingLeagueName = 10
  const val paddingName = 25
  const val paddingPosition = 4
  const val paddingSeason = 10
  const val paddingTeamAbbrev = 6
  const val paddingTeamName = 25
}