package ca.sebleclerc.hockeydata.core.domain

import ca.sebleclerc.hockeydata.core.helpers.Formatter
import java.sql.ResultSet

class PlayerSalarySeason(
  val salary: Int,
  val season: Season,
) {
  companion object

  val avv: String
    get() = Formatter.intToSalary(salary)
}

fun PlayerSalarySeason.Companion.fromRow(rs: ResultSet): PlayerSalarySeason =
  PlayerSalarySeason(
    salary = rs.getInt("avv"),
    season = Season(rs.getInt("season")),
  )
