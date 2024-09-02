package ca.sebleclerc.hockeydata.models

import ca.sebleclerc.hockeydata.helpers.Constants
import java.io.File

sealed class CacheStep(private val endpoint: String, private val fileName: String) {
  class CacheTeamRoster(val team: Team) : CacheStep(
    "/roster/${team.abbreviation}/${Constants.currentSeason}",
    "teams-${team.id}-roster.json"
  )
  class Positions : CacheStep("/positions", "positions.json")
  class Teams : CacheStep("/team", "teams.json")

  // CacheStep

  val file = File("/Users/sleclerc/Developer/hockeydata/json/$fileName")

  val apiPath: String
    get() = when (this) {
        is CacheTeamRoster -> "https://api-web.nhle.com/v1/roster/${team.abbreviation}/current"
        is Positions -> "TODO()"
        is Teams -> "https://api.nhle.com/stats/rest/en$endpoint"
    }
}