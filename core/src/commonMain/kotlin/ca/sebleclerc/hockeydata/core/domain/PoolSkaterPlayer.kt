package ca.sebleclerc.hockeydata.core.domain

import java.math.BigDecimal
import java.math.RoundingMode

class PoolSkaterPlayer(
  val player: Player,
  val seasons: List<PlayerSkaterSeason>,
  val salary: PlayerSalarySeason?,
  val team: Team?,
  val current: PlayerSkaterSeason?,
) {
  val averagePoints = seasons.map { it.poolPoints }.average()
  val averageGames = seasons.map { it.games }.average()

  val poolValue: String
    get() {
      val lastSeason = Season(20242025)
      val lastSeasonPoints = seasons.firstOrNull { it.season == lastSeason }

      if (lastSeasonPoints == null) return ""
      if (salary == null) return ""

      val value = lastSeasonPoints.poolPoints.toDouble() / salary.salary * 100000
      return BigDecimal(value)
        .setScale(5, RoundingMode.HALF_EVEN)
        .toString()
    }

  val averagePoolValue: String
    get() {
      if (salary == null) return ""

      val value = averagePoints / salary.salary * 100000
      return BigDecimal(value)
        .setScale(5, RoundingMode.HALF_EVEN)
        .toString()
    }

  val history: List<String>
    get() =
      seasons
        .map {
          val pPoints = it.poolPoints
          val season = it.season
          "$pPoints[${season.compact}]"
        }
}
