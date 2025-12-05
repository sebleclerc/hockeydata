package ca.sebleclerc.hockeydata.core.helpers

import ca.sebleclerc.hockeydata.core.domain.Season

object Constants {
  val currentSeason = Season(20252026)

  const val DB_URL = "jdbc:mariadb://127.0.0.1:3306/hockeydata"
  const val DB_USER = "sleclerc"
  const val DB_PWD = "sleclerc"

  // Export
  const val JSON_FOLDER = "/Users/sleclerc/Developer/hockeydata/json"

  // CLI
  const val PADDING_AVV = 15
  const val PADDING_FLOAT = 10
  const val PADDING_ID = 8
  const val PADDING_INT = 5
  const val PADDING_LEAGUE_NAME = 10
  const val PADDING_NAME = 25
  const val PADDING_POSITION = 4
  const val PADDING_SEASON = 10
  const val PADDING_TEAM_ABBREV = 6
  const val PADDING_TEAM_NAME = 25

  // SHARED UI
  const val UI_PADDING_AVV = 110
  const val UI_PADDING_ID = 70
  const val UI_PADDING_NAME = 175
  const val UI_PADDING_POSITION = 30
  const val UI_PADDING_TEAM_ABBREV = 45
  const val UI_PADDING_HISTORY = 85
  const val UI_ROW_HEIGHT = 25
}
