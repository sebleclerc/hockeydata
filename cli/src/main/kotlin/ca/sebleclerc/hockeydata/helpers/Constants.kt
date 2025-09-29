@file:Suppress("ConstPropertyName")

package ca.sebleclerc.hockeydata.helpers

import ca.sebleclerc.hockeydata.models.Season

object Constants {
  const val jsonFolder = "/Users/sleclerc/Developer/hockeydata/json"
  val currentSeason = Season(20252026)

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