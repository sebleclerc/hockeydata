package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
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
      LoggerColumn.Postion(),
      LoggerColumn.Custom("Average", averagePadding),
      LoggerColumn.Custom("History", historyPadding)
    )

    val players = mutableListOf<Pair<PoolSkaterPlayer, Double>>()

    di.database.getAllPlayers(false).forEach { player ->
      val seasons = di.database.getSeasonsForSkaterId(player.id)
      players.add(
        Pair(
          PoolSkaterPlayer(player, seasons),
          seasons.map { it.poolPoints }.average()
        )
      )
    }

    players
      .filter { it.second > 30 }
      .sortedByDescending { it.second }
      .forEach { element ->
        val rows = mutableListOf(
          LoggerColumn.ID(element.first.player.id),
          LoggerColumn.Name(element.first.player.fullName),
          LoggerColumn.Postion(element.first.player.positionCode),
          LoggerColumn.Custom(
            BigDecimal(element.second)
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
//          .map { it.poolPoints }
//          .joinToString(separator = ",")
        rows.addAll(history)

        Logger.row(*rows.toTypedArray())
//        Logger.row(
//          LoggerColumn.ID(element.first.player.id),
//          LoggerColumn.Name(element.first.player.fullName),
//          LoggerColumn.Postion(element.first.player.positionCode),
//          LoggerColumn.Custom(
//            BigDecimal(element.second)
//              .setScale(2, RoundingMode.HALF_EVEN)
//              .toString(),
//            padding = averagePadding),
//          LoggerColumn.Custom(history, 7)
//        )
      }

//      var row = "${player.fullName} : ".padStart(25, ' ')
//      seasons.forEach { season ->
//        val average = BigDecimal((season.poolPoints / season.games).toDouble()).setScale(2, RoundingMode.HALF_EVEN)
//        val tmpSeason = season.season.toString().drop(2)
//        val start = tmpSeason.take(2)
//        val end = tmpSeason.takeLast(2)
//        val pointPart = "$start-$end[${season.poolPoints}]($average)".padStart(20, ' ')
//        row += pointPart
//      }
//
//      Logger.info(row)

    Logger.taskEnd()
  }
}