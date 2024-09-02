package ca.sebleclerc.hockeydata.models

import java.sql.ResultSet

data class Player(
  val id: Int,
  val firstName: String,
  val lastName: String,
  val primaryNumber: Int,
  val birthDate: BirthDate,
  val height: Int,
  val weight: Int,
  val shoot: String,
  val rookie: Boolean,
  val teamId: Int,
  val positionCode: String,
  val headshotUrl: String,
  val heroImageUrl: String
) {
  companion object
}

// Row
fun Player.Companion.fromRow(rs: ResultSet): Player {
  return Player(
    rs.getInt("id"),
    rs.getString("firstName"),
    rs.getString("lastName"),
    rs.getInt("primaryNumber"),
    BirthDate.fromRow(rs),
    rs.getInt("height"),
    rs.getInt("weight"),
    rs.getString("shoot"),
    rs.getBoolean("rookie"),
    rs.getInt("teamId"),
    rs.getString("positionCode"),
    rs.getString("headshotUrl"),
    rs.getString("heroImageUrl"),
  )
}