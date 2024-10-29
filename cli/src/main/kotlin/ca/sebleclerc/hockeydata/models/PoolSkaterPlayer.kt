package ca.sebleclerc.hockeydata.models

import ca.sebleclerc.hockeydata.helpers.Logger
import java.math.BigDecimal
import java.math.RoundingMode

class PoolSkaterPlayer(
  val player: Player,
  val seasons: List<PlayerSkaterSeason>,
  val salary: PlayerSalarySeason?,
  val team: Team?,
  val current: PlayerSkaterSeason?
) {
  val averagePoints = seasons.map { it.poolPoints }.average()
  val averageGames = seasons.map { it.games }.average()

  val poolValue: String
    get() {
      val lastSeason = Season(20232024)
      val lastSeasonPoints = seasons.firstOrNull { it.season == lastSeason }
      if (lastSeasonPoints != null) else return ""
      if (salary != null) else return ""

      val value =  lastSeasonPoints.poolPoints.toDouble() / salary.salary * 100000
      return BigDecimal(value)
        .setScale(5, RoundingMode.HALF_EVEN)
        .toString()
    }

  val averagePoolValue: String
    get() {
      if (salary != null) else return ""

      val value = averagePoints / salary.salary * 100000
      return BigDecimal(value)
        .setScale(5, RoundingMode.HALF_EVEN)
        .toString()
    }
}
