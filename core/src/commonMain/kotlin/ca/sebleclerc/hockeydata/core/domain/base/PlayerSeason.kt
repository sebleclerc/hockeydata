package ca.sebleclerc.hockeydata.core.domain.base

import ca.sebleclerc.hockeydata.core.domain.Season

abstract class PlayerSeason(
  val season: Season,
  val league: String,
  val team: String,
  val games: Int,
  val poolPoints: Float,
)