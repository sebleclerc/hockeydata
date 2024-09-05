package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.PoolHelper
import ca.sebleclerc.hockeydata.models.PoolSkaterPlayer
import java.math.BigDecimal
import java.math.RoundingMode

class PoolPreviewCommand(di: DI) : BaseCommand(di, name = "preview") {
  override fun run() {
    super.run()

    Logger.taskTitle("Pool Preview")

    di.database.getAllPlayers(false).forEach { player ->
      val seasons = di.database.getSeasonsForSkaterId(player.id)
//      val poolPlayer = PoolSkaterPlayer(player, seasons)

      var row = "${player.fullName} : ".padStart(25, ' ')
      seasons.forEach { season ->
        val average = BigDecimal((season.poolPoints / season.games).toDouble()).setScale(2, RoundingMode.HALF_EVEN)
        val tmpSeason = season.season.toString().drop(2)
        val start = tmpSeason.take(2)
        val end = tmpSeason.takeLast(2)
        val pointPart = "$start-$end[${season.poolPoints}]($average)".padStart(20, ' ')
        row += pointPart
      }

      Logger.info(row)
    }

    Logger.taskEnd()
  }
}