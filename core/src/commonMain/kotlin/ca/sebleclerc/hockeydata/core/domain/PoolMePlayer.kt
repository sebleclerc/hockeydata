package ca.sebleclerc.hockeydata.core.domain

class PoolMePlayer(
  val player: Player,
  val salary: PlayerSalarySeason?,
  val stats: PlayerSkaterSeason?,
) {
  val games: Int
    get() = stats?.games ?: 0

  val goals: Int
    get() = stats?.goals ?: 0

  val assists: Int
    get() = stats?.assists ?: 0

  val points: Int
    get() = stats?.points ?: 0

  val poolPoints: Float
    get() = stats?.poolPoints ?: 0F

  val avv: String
    get() = salary?.avv ?: "N/A"
}
