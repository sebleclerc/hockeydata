package ca.sebleclerc.hockeydata.core.cache

import kotlinx.serialization.Serializable

@Serializable
data class CacheDefault(
  val default: String,
)