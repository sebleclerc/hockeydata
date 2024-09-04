package ca.sebleclerc.hockeydata.models.cache

import kotlinx.serialization.Serializable

@Serializable
data class CacheDefault(
  val default: String
)