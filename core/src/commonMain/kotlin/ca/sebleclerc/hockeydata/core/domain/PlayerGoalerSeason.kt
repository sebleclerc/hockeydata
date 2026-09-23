package ca.sebleclerc.hockeydata.core.domain

import ca.sebleclerc.hockeydata.core.domain.base.PlayerSeason

class PlayerGoalerSeason(
  season: Season,
  league: String,
  team: String,
  games: Int,
  val gamesStarted: Int,
  val ot: Int,
  val shutouts: Int,
  val wins: Int,
  val losses: Int,
  val savePercentage: Float,
  poolPoints: Float,
) : PlayerSeason(season, league, team, games, poolPoints) {
  companion object
}
