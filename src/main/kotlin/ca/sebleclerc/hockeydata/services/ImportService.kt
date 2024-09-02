package ca.sebleclerc.hockeydata.services

import ca.sebleclerc.hockeydata.models.CacheRoster
import ca.sebleclerc.hockeydata.models.CacheStep
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class ImportService(private val dbService: DatabaseService) {
  private val json = Json {
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

  private fun getCachedData(step: CacheStep): JsonObject? {
    val fileContent = step.file.readText()
    val jsonObject = json.parseToJsonElement(fileContent).jsonObject

    return jsonObject["data"]?.jsonObject
  }
}