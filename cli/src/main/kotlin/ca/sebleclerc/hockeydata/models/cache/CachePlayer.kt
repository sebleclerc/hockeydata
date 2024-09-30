package ca.sebleclerc.hockeydata.models.cache

import kotlinx.serialization.Serializable

@Serializable
data class CachePlayer(
  val playerId: Int,
  val firstName: CacheDefault,
  val lastName: CacheDefault,
  val sweaterNumber: Int? = null,
  val birthDate: String,
  val birthCity: CacheDefault? = null,
  val birthStateProvince: CacheDefault? = null,
  val birthCountry: String? = null,
  val heightInInches: Int,
  val weightInPounds: Int,
  val isActive: Boolean,
  val shootsCatches: String,
  val currentTeamId: Int? = null,
  val position: String,
  val headshot: String,
  val heroImage: String
)

@Serializable
data class CacheSkaterPlayer(
  val seasonTotals: List<CacheSkaterSeason>
)

@Serializable
data class CacheSkaterSeason(
  val season: Int,
  val leagueAbbrev: String,
  val gamesPlayed: Int? = null,
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
  val teamName: CacheDefault,
  val gameTypeId: Int
) {
  val averageTotalOnIce: Float?
    get() = convertStringToFloat(avgToi)
}

@Serializable
data class CacheGoalerPlayer(
  val seasonTotals: List<CacheGoalerSeason>
)

@Serializable
data class CacheGoalerSeason(
  val season: Int,
  val gamesPlayed: Int? = null,
  val gamesStarted: Int? = null,
  val timeOnIce: String? = null,
  val goals: Int? = null,
  val assists: Int? = null,
  val goalsAgainst: Int? = null,
  val goalsAgainstAvg: Float? = null,
  val shotsAgainst: Int? = null,
  val savePctg: Float? = null,
  val shutouts: Int? = null,
  val leagueAbbrev: String,
  val wins: Int? = null,
  val ties: Int? = null,
  val losses: Int? = null,
  val otLosses: Int? = null,
  val pim: Int? = null,
  val teamName: CacheDefault,
  val gameTypeId: Int
) {
  val averageTotalOnIce: Float?
    get() = convertStringToFloat(timeOnIce)
}

private fun convertStringToFloat(toi: String?): Float? {
  if (toi == null) return null

  val sections = toi.split(":")
  val seconds = sections[1].toInt()
  val secondsPercentage = seconds / 60F
  val finalValue = sections[0].toFloat() + secondsPercentage

  return finalValue
}
