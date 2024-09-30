package ca.sebleclerc.hockeydata.helpers

import ca.sebleclerc.hockeydata.models.Player
import ca.sebleclerc.hockeydata.models.PlayerSkaterSeason
import ca.sebleclerc.hockeydata.models.cache.CachePlayer
import ca.sebleclerc.hockeydata.models.cache.CacheSkaterSeason

object PoolHelper {
  fun getSkaterPoolPoint(player: CachePlayer, season: CacheSkaterSeason): Float {
    return when(player.position) {
      "D" -> getSkaterPoolPointsForDefenseman(season.goals ?: 0, season.assists ?: 0)
      else -> getSkaterPoolPointsForForward(season.goals ?: 0, season.assists ?: 0)
    }
  }

  fun getSkaterPoolPoints(player: Player, season: PlayerSkaterSeason): Float {
    return when(player.positionCode) {
      "D" -> getSkaterPoolPointsForDefenseman(season.goals, season.assists)
      "C", "L", "R" -> getSkaterPoolPointsForForward(season.goals, season.assists)
      else -> 0F
    }
  }

  private fun getSkaterPoolPointsForDefenseman(goals: Int, assists: Int): Float {
    return goals * 3 + assists * 1.5F
  }

  private fun getSkaterPoolPointsForForward(goals: Int, assists: Int): Float {
    return (goals * 2 + assists).toFloat()
  }
}