package ca.sebleclerc.hockeydata.models

import java.sql.ResultSet

data class BirthDate(
  val year: Int,
  val month: Int,
  val day: Int,
  val city: String,
  val province: String?,
  val country: String
) {
  companion object
}

// From ResultSet
fun BirthDate.Companion.fromRow(rs: ResultSet): BirthDate {
  return BirthDate(
    rs.getInt("birthYear"),
    rs.getInt("birthMonth"),
    rs.getInt("birthDay"),
    rs.getString("birthCity"),
    rs.getString("birthProvince"),
    rs.getString("birthCountry"),
  )
}