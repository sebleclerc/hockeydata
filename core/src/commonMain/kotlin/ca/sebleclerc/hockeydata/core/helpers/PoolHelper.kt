package ca.sebleclerc.hockeydata.core.helpers

import ca.sebleclerc.hockeydata.core.cache.CachePlayer
import ca.sebleclerc.hockeydata.core.cache.CacheSkaterSeason
import ca.sebleclerc.hockeydata.core.domain.Player
import ca.sebleclerc.hockeydata.core.domain.PlayerSkaterSeason

object PoolHelper {
  fun getSkaterPoolPoint(
    player: CachePlayer,
    season: CacheSkaterSeason,
  ): Float =
    when (player.position) {
      "D" -> getSkaterPoolPointsForDefenseman(season.goals ?: 0, season.assists ?: 0)
      else -> getSkaterPoolPointsForForward(season.goals ?: 0, season.assists ?: 0)
    }

  fun getSkaterPoolPoints(
    player: Player,
    season: PlayerSkaterSeason,
  ): Float =
    when (player.positionCode) {
      "D" -> getSkaterPoolPointsForDefenseman(season.goals, season.assists)
      "C", "L", "R" -> getSkaterPoolPointsForForward(season.goals, season.assists)
      else -> 0F
    }

  private fun getSkaterPoolPointsForDefenseman(
    goals: Int,
    assists: Int,
  ): Float = goals * 3 + assists * 1.5F

  private fun getSkaterPoolPointsForForward(
    goals: Int,
    assists: Int,
  ): Float = (goals * 2 + assists).toFloat()
}