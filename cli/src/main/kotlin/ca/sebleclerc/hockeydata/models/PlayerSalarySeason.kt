package ca.sebleclerc.hockeydata.models

import ca.sebleclerc.hockeydata.helpers.Formatter
import java.sql.ResultSet

class PlayerSalarySeason(val salary: Int, val season: Season) {
  companion object

  val avv: String
    get() = Formatter.intToSalary(salary)
}

fun PlayerSalarySeason.Companion.fromRow(rs: ResultSet): PlayerSalarySeason {
  return PlayerSalarySeason(
    salary = rs.getInt("avv"),
    season = Season(rs.getInt("season"))
  )
}