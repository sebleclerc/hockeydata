package ca.sebleclerc.hockeydata.models.cache

import kotlinx.serialization.Serializable

@Serializable
data class CacheRoster(
  val forwards: List<CacheRosterPlayer>,
  val defensemen: List<CacheRosterPlayer>,
  val goalies: List<CacheRosterPlayer>
)

@Serializable
data class CacheRosterPlayer(val id: Int)
