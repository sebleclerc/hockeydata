package ca.sebleclerc.hockeydata.models.cache

import kotlinx.serialization.Serializable

@Serializable
data class CachePlayer(
  val playerId: Int,
  val firstName: CachePlayerDefault,
  val lastName: CachePlayerDefault,
  val sweaterNumber: Int? = null,
  // birthday part1,2,3
  val birthCity: CachePlayerDefault,
  val birthStateProvince: CachePlayerDefault? = null,
  val birthCountry: String,
  val heightInInches: Int,
  val weightInPounds: Int,
  val isActive: Boolean,
  val shootsCatches: String,
  val currentTeamId: Int,
  val position: String,
  val headshot: String,
  val heroImage: String
)

@Serializable
data class CachePlayerDefault(
  val default: String
)
