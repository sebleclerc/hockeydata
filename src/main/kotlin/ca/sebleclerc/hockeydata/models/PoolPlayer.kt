package ca.sebleclerc.hockeydata.models

import ca.sebleclerc.hockeydata.helpers.PoolHelper

class PoolSkaterPlayer(
  val player: Player,
  val seasons: List<PlayerSkaterSeason>
) {
//  val averageGames: Float
//  val averagePoints: Float

  init {
    val pointsPerSeason = seasons.map { season ->
      val poolPoints =  PoolHelper.getSkaterPoolPoints(player, season)}
  }
}
