package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.models.PoolDraftStatut
import ca.sebleclerc.hockeydata.models.PoolSkaterPlayer
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import java.math.BigDecimal
import java.math.RoundingMode

class PoolPreviewCommand(di: DI) : BaseCommand(di, name = "preview") {
  private val current: Boolean? by option("-c", "--current").flag()
  private val sortValue by option("--sortValue").flag()
  private val teamId by option("-t", "--team").int()
  private val name by option("-n", "--name")
  private val minimal by option("-m", "--minimal").flag()

  override fun run() {
    super.run()

    Logger.taskTitle("Pool Preview")

    val statuses = di.database.getAllPoolDraftStatuses()
    val averagePadding = 10
    val valuePadding = 10
    val currentPadding = 7

    val headers = mutableListOf(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Position(),
      LoggerColumn.Team(),
      LoggerColumn.Salary()
    )

//    if (current == true) {
      headers.add(LoggerColumn.Custom("Cur.", padding = currentPadding))
//    }

    headers.addAll(
      listOf(
        LoggerColumn.Custom("Average", averagePadding),
        LoggerColumn.Custom("V. Last", valuePadding),
        LoggerColumn.Custom("V. Avg.", valuePadding),
        LoggerColumn.Custom("History", 10)
      )
    )

    Logger.header(*headers.toTypedArray())

    val players = mutableListOf<PoolSkaterPlayer>()
    val dbPlayers = if (teamId != null) {
      di.database.getPlayersForTeam(teamId!!)
    } else {
      di.database.getAllPlayers(false)
    }

    dbPlayers.forEach { player ->
      val status = statuses[player.id]
      if (status == null || status == PoolDraftStatut.AVAILABLE) {
        val seasons = di.database.getLastSeasonsForSkaterId(player.id)
        val salary = di.database.getPlayerSeasonSalary(Constants.currentSeason, player.id)
        val team = di.database.getTeamForId(player.teamId)
        val current = di.database.getSingleSeasonForSkateId(player.id, Constants.currentSeason)
//        val current = if (current == true) di.database.getSingleSeasonForSkateId(player.id, Constants.currentSeason) else null

        players.add(PoolSkaterPlayer(player, seasons, salary, team, current))
      }
    }

    players
      .filter {
        if (name != null) {
          it.player.fullName.contains(name!!)
        } else if (teamId != null) {
          it.averagePoints > -1
        } else if (minimal) {
          it.averagePoints > 20
        } else {
          it.averagePoints > -1
        }
      }
      .sortedWith(compareBy {
        if (current == true) {
          it.current?.poolPoints?.toDouble()
        } else if (sortValue) {
          it.poolValue
        } else {
          it.averagePoints
        }
      })
      .reversed()
      .forEach { element ->
        val rows = mutableListOf(
          LoggerColumn.ID(element.player.id),
          LoggerColumn.Name(element.player.fullName),
          LoggerColumn.Position(element.player.positionCode),
          LoggerColumn.Team(element.team?.abbreviation ?: "N/A"),
          LoggerColumn.Salary(element.salary?.avv ?: "N/A"),
        )

//        if (current == true) {
          rows.add(
            LoggerColumn.Custom(
              (element.current?.poolPoints ?: 0F).toString(),
              padding = currentPadding
            )
          )
//        }

        rows.addAll(listOf(
          LoggerColumn.Custom(
            BigDecimal(element.averagePoints)
              .setScale(2, RoundingMode.HALF_EVEN)
              .toString(),
            padding = averagePadding),
          LoggerColumn.Custom(element.poolValue, valuePadding),
          LoggerColumn.Custom(element.averagePoolValue, valuePadding)
        ))
        val history = element
          .seasons
          .map {
            val pPoints = it.poolPoints
            val season = it.season
            LoggerColumn.Custom("${pPoints}[${season.compact}]", 14)
          }
        rows.addAll(history)

        Logger.row(*rows.toTypedArray())
      }

    Logger.taskEnd()
  }
}