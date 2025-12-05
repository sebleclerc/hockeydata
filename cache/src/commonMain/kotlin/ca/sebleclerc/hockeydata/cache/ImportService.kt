package ca.sebleclerc.hockeydata.cache

import ca.sebleclerc.hockeydata.core.cache.CacheGoalerPlayer
import ca.sebleclerc.hockeydata.core.cache.CachePlayer
import ca.sebleclerc.hockeydata.core.cache.CacheRoster
import ca.sebleclerc.hockeydata.core.cache.CacheSkaterPlayer
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.core.helpers.Logger
import ca.sebleclerc.hockeydata.database.DatabaseService
import kotlinx.serialization.json.Json

class ImportService(
  private val dbService: DatabaseService,
) {
  private val json =
    Json {
      ignoreUnknownKeys = true
    }

  fun importRosters() {
    dbService.clearRosters()

    val teams = dbService.getAllTeams()

    teams.forEach { team ->
      val step = CacheStep.CacheTeamRoster(team)
      val fileContent = step.file.readText()
      val roster = json.decodeFromString<CacheRoster>(fileContent)

      dbService.insertTeamRoster(team, roster.forwards)
      dbService.insertTeamRoster(team, roster.defensemen)
      dbService.insertTeamRoster(team, roster.goalies)
    }
  }

  fun importPlayers(steps: List<CacheStep.Player>) {
    steps.forEach { step ->
      Logger.debug("Import player ${step.playerId}")
      val fileContent = step.file.readText()
      val cachePlayer = json.decodeFromString<CachePlayer>(fileContent)

      dbService.insertPlayers(cachePlayer)

      if (cachePlayer.position == "G") {
        val goaler = json.decodeFromString<CacheGoalerPlayer>(fileContent)
        goaler.seasonTotals.forEach { dbService.insertGoalerSeason(step.playerId, it) }
      } else {
        val skater = json.decodeFromString<CacheSkaterPlayer>(fileContent)
        skater.seasonTotals.forEach { dbService.insertSkaterSeason(cachePlayer, it) }
      }
    }
  }
}
