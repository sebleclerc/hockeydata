package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.models.PlayerSalarySeason
import ca.sebleclerc.hockeydata.models.PoolSkaterPlayer
import java.math.BigDecimal
import java.math.RoundingMode

class PoolPreviewCommand(di: DI) : BaseCommand(di, name = "preview") {
  override fun run() {
    super.run()

    Logger.taskTitle("Pool Preview")

    val averagePadding = 12
    val historyPadding = 10

    Logger.header(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Position(),
      LoggerColumn.Salary(),
      LoggerColumn.Custom("Average", averagePadding),
      LoggerColumn.Custom("History", 10)
    )

    val players = mutableListOf<Triple<PoolSkaterPlayer, PlayerSalarySeason?, Double>>()

    di.database.getAllPlayers(false).forEach { player ->
      val seasons = di.database.getSeasonsForSkaterId(player.id)
      val salary = di.database.getPlayerSeasonSalary(Constants.currentSeason, player.id)

      players.add(
        Triple(
          PoolSkaterPlayer(player, seasons),
          salary,
          seasons.map { it.poolPoints }.average()
        )
      )
    }

    players
      .filter { it.third > 30 }
      .sortedByDescending { it.third }
      .forEach { element ->
        val rows = mutableListOf(
          LoggerColumn.ID(element.first.player.id),
          LoggerColumn.Name(element.first.player.fullName),
          LoggerColumn.Position(element.first.player.positionCode),
          LoggerColumn.Salary(element.second?.avv ?: "N/A"),
          LoggerColumn.Custom(
            BigDecimal(element.third)
              .setScale(2, RoundingMode.HALF_EVEN)
              .toString(),
            padding = averagePadding),
        )
        val history = element
          .first
          .seasons
          .map {
            val pPoints = it.poolPoints
            val season = it.season
            LoggerColumn.Custom("${pPoints}[${season.compact}]", 17)
          }
        rows.addAll(history)

        Logger.row(*rows.toTypedArray())
      }

    Logger.taskEnd()
  }
}