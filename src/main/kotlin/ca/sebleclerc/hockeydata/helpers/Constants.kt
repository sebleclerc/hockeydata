@file:Suppress("ConstPropertyName")

package ca.sebleclerc.hockeydata.helpers

import ca.sebleclerc.hockeydata.models.Season

object Constants {
  const val jsonFolder = "/Users/sleclerc/Developer/hockeydata/json"
  val currentSeason = Season(20242025)

  const val paddingAVV = 15
  const val paddingFloat = 10
  const val paddingId = 8
  const val paddingInt = 5
  const val paddingLeagueName = 10
  const val paddingName = 30
  const val paddingPosition = 5
  const val paddingSeason = 10
}