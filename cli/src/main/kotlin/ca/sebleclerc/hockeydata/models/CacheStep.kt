package ca.sebleclerc.hockeydata.models

import java.io.File

sealed class CacheStep(private val fileName: String) {
  class CacheTeamRoster(val team: Team) : CacheStep("teams-${team.id}-roster.json")
  class Positions : CacheStep("positions.json")
  class Teams : CacheStep( "teams.json")
  class Player(val playerId: Int) : CacheStep("$playerId-player.json")

  // CacheStep

  val file = File("/Users/sleclerc/Developer/hockeydata/json/$fileName")

  val apiPath: String
    get() = when (this) {
      is CacheTeamRoster -> "https://api-web.nhle.com/v1/roster/${team.abbreviation}/current"
      is Positions -> "/positions"
      is Teams -> "https://api.nhle.com/stats/rest/en/teams"
      is Player -> "https://api-web.nhle.com/v1/player/$playerId/landing"
    }
}