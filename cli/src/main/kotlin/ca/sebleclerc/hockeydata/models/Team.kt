package ca.sebleclerc.hockeydata.models

import java.sql.ResultSet

data class Team(
  val id: Int,
  val name: String,
  val venue: String,
  val abbreviation: String,
  val firstYearOfPlay: String,
  val divisionId: Int,
  val conferenceId: Int,
  val franchiseId: Int,
  val active: Boolean
) {
  companion object
}

// Import from ResultSet
fun Team.Companion.fromResult(rs: ResultSet): Team {
  return Team(
    rs.getInt("id"),
    rs.getString("name"),
    rs.getString("venue"),
    rs.getString("abbreviation"),
    rs.getString("firstYearOfPlay"),
    rs.getInt("divisionId"),
    rs.getInt("conferenceId"),
    rs.getInt("franchiseId"),
    rs.getBoolean("active")
  )
}