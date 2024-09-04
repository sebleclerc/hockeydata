package ca.sebleclerc.hockeydata.models

import java.sql.ResultSet

data class PlayerSkaterSeason(
  val season: Int,
  val league: String,
  val team: String,
  val games: Int,
  val goals: Int,
  val assists: Int,
  val points: Int,
  val poolPoints: Float
) {
  companion object
}

fun PlayerSkaterSeason.Companion.fromRow(rs: ResultSet): PlayerSkaterSeason {
  return PlayerSkaterSeason(
    rs.getInt("season"),
    rs.getString("leagueName"),
    rs.getString("teamName"),
    rs.getInt("games"),
    rs.getInt("goals"),
    rs.getInt("assists"),
    rs.getInt("points"),
    rs.getFloat("poolPoints")
  )
}