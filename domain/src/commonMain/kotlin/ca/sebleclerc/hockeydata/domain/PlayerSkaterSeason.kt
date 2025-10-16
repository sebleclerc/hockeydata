package ca.sebleclerc.hockeydata.domain

data class PlayerSkaterSeason(
  val season: Season,
  val league: String,
  val team: String,
  val games: Int,
  val goals: Int,
  val assists: Int,
  val points: Int,
  val poolPoints: Float,
) {
  companion object
}
