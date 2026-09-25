package ca.sebleclerc.hockeydata.core.domain

import ca.sebleclerc.hockeydata.core.domain.base.PlayerSeason
import java.math.BigDecimal
import java.math.RoundingMode

abstract class PoolPlayer(
  val player: Player,
  val salary: PlayerSalarySeason?,
  val team: Team?,
) {
  val averageGames by lazy {
    seasons.map { it.games }.average()
  }
  val averagePoints by lazy {
    if (seasons.isEmpty()) 0.0 else seasons.map { it.poolPoints }.average()
  }

  abstract val seasons: List<PlayerSeason>

  val poolValue: Double
    get() {
      val lastSeason = Season(20252026)
      val lastSeasonPoints = seasons.firstOrNull { it.season == lastSeason }

      if (lastSeasonPoints == null) return 0.0
      if (salary == null) return 0.0

      val value = lastSeasonPoints.poolPoints.toDouble() / salary.salary * 100000
      return value
    }

  val averagePoolValue: String
    get() {
      if (salary == null) return ""

      val value = averagePoints / salary.salary * 100000

      if (value == 0.0 || value.isNaN() || value.isInfinite()) {
        return ""
      }

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
        }.padEnd(5, "")
}

private fun <T> List<T>.padEnd(
  targetLength: Int,
  paddingElement: T,
): List<T> {
  val currentSize = this.size
  if (currentSize >= targetLength) {
    return this
  }
  val paddingCount = targetLength - currentSize
  val paddingList = List(paddingCount) { paddingElement }
  return this + paddingList // Or this.toMutableList().apply { addAll(paddingList) }
}