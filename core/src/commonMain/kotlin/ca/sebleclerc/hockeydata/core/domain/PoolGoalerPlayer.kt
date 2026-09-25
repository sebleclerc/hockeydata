package ca.sebleclerc.hockeydata.core.domain

class PoolGoalerPlayer(
  player: Player,
  override val seasons: List<PlayerGoalerSeason>,
  salary: PlayerSalarySeason?,
  team: Team?,
  val current: PlayerGoalerSeason?,
) : PoolPlayer(player, salary, team)
