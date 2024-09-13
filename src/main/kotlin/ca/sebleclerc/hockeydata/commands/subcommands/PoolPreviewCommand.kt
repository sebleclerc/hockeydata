package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.models.PoolSkaterPlayer
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import java.math.BigDecimal
import java.math.RoundingMode

class PoolPreviewCommand(di: DI) : BaseCommand(di, name = "preview") {
  private val sortValue by option("--sortValue").flag()

  override fun run() {
    super.run()

    Logger.taskTitle("Pool Preview")

    val averagePadding = 12
    val valuePadding = 12

    Logger.header(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Position(),
      LoggerColumn.Salary(),
      LoggerColumn.Custom("Average", averagePadding),
      LoggerColumn.Custom("V. Last", valuePadding),
      LoggerColumn.Custom("V. Avg.", valuePadding),
      LoggerColumn.Custom("History", 10)
    )

    val players = mutableListOf<PoolSkaterPlayer>()

    di.database.getAllPlayers(false).forEach { player ->
      val seasons = di.database.getSeasonsForSkaterId(player.id)
      val salary = di.database.getPlayerSeasonSalary(Constants.currentSeason, player.id)

      players.add(PoolSkaterPlayer(player, seasons, salary))
    }

    players
      .filter { it.averagePoints > 30 }
      .sortedWith(compareBy { if (sortValue) it.poolValue else it.averagePoints })
      .reversed()
      .forEach { element ->
        val rows = mutableListOf(
          LoggerColumn.ID(element.player.id),
          LoggerColumn.Name(element.player.fullName),
          LoggerColumn.Position(element.player.positionCode),
          LoggerColumn.Salary(element.salary?.avv ?: "N/A"),
          LoggerColumn.Custom(
            BigDecimal(element.averagePoints)
              .setScale(2, RoundingMode.HALF_EVEN)
              .toString(),
            padding = averagePadding),
          LoggerColumn.Custom(element.poolValue, valuePadding),
          LoggerColumn.Custom(element.averagePoolValue, valuePadding)
        )
        val history = element
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