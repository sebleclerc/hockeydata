package ca.sebleclerc.hockeydata.models.cache

import kotlinx.serialization.Serializable

@Serializable
data class CachePlayer(
  val playerId: Int,
  val firstName: CachePlayerDefault,
  val lastName: CachePlayerDefault,
  val sweaterNumber: Int? = null,
  // birthday part1,2,3
  val birthCity: CachePlayerDefault? = null,
  val birthStateProvince: CachePlayerDefault? = null,
  val birthCountry: String,
  val heightInInches: Int,
  val weightInPounds: Int,
  val isActive: Boolean,
  val shootsCatches: String,
  val currentTeamId: Int,
  val position: String,
  val headshot: String,
  val heroImage: String,
  val seasonTotals: List<CachePlayerSeason>
)

@Serializable
data class CachePlayerDefault(
  val default: String
)

@Serializable
data class CachePlayerSeason(
  val season: Int,
  val leagueAbbrev: String,
  val gamesPlayed: Int,
  val goals: Int? = null,
  val assists: Int? = null,
  val points: Int? = null,
  val shots: Int? = null,
  val pim: Int? = null,
  val plusMinus: Int? = null,
  val avgToi: String? = null,
  val faceoffWinningPctg: Float? = null,
  val gameWinningGoals: Int? = null,
  val powerPlayGoals: Int? = null,
  val powerPlayPoints: Int? = null,
  val shorthandedGoals: Int? = null,
  val shorthandedPoints: Int? = null,
  val otGoals: Int? = null,
  val shootingPctg: Float? = null,
  val teamName: CachePlayerDefault,
  val gameTypeId: Int
) {
  val averageTotalOnIce: Float?
    get() {
      if (avgToi == null) return null

      val sections = avgToi!!.split(":")
      val seconds = sections[1].toInt()
      val secondsPercentage = seconds / 60F
      val finalValue = sections[0].toFloat() + secondsPercentage

      return finalValue
    }
}
