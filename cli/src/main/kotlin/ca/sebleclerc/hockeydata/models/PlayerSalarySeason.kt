package ca.sebleclerc.hockeydata.models

import java.sql.ResultSet

class PlayerSalarySeason(val salary: Int, val season: Season) {
  companion object

  val avv: String
    get() {
      var formatted = when {
        salary < 1000000 -> StringBuilder(salary.toString())
          .insert(3, ' ').toString()
        salary < 10000000 -> StringBuilder(salary.toString())
          .insert(1, ' ')
          .insert(5, ' ')
          .toString()
        salary >= 10000000 -> StringBuilder(salary.toString())
          .insert(2, ' ')
          .insert(6, ' ')
          .toString()
        else -> "0"
      }

      formatted += " $"

      return formatted
    }
}

fun PlayerSalarySeason.Companion.fromRow(rs: ResultSet): PlayerSalarySeason {
  return PlayerSalarySeason(
    salary = rs.getInt("avv"),
    season = Season(rs.getInt("season"))
  )
}