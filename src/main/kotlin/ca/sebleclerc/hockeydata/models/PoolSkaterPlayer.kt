package ca.sebleclerc.hockeydata.models

class PoolSkaterPlayer(
  val player: Player,
  val seasons: List<PlayerSkaterSeason>,
  val salary: PlayerSalarySeason?
) {
  val averagePoints = seasons.map { it.poolPoints }.average()
  val averageGames = seasons.map { it.games }.average()
}
