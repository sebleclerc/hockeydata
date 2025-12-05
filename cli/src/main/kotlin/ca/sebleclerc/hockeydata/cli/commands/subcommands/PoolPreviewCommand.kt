package ca.sebleclerc.hockeydata.cli.commands.subcommands

import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.commands.BaseCommand
import ca.sebleclerc.hockeydata.cli.helpers.Logger
import ca.sebleclerc.hockeydata.cli.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.core.helpers.Formatter
import ca.sebleclerc.hockeydata.shared.viewmodels.SharedPoolPreviewViewModel
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class PoolPreviewCommand(
  di: DI,
) : BaseCommand(di, name = "preview") {
  private val current: Boolean? by option("-c", "--current").flag()
  private val sortValue by option("--sortValue").flag()
  private val teamId by option("-t", "--team").int()
  private val name by option("-n", "--name")
  private val minimal by option("-m", "--minimal").flag()

  override fun run() {
    super.run()

    Logger.taskTitle("Pool Preview")

    val viewModel = SharedPoolPreviewViewModel(di.database)

    val averagePadding = 10
    val valuePadding = 10
    val currentPadding = 7

    val headers =
      mutableListOf(
        LoggerColumn.ID(),
        LoggerColumn.Name(),
        LoggerColumn.Position(),
        LoggerColumn.Team(),
        LoggerColumn.Salary(),
      )

//    if (current == true) {
    headers.add(LoggerColumn.Custom("Cur.", padding = currentPadding))
//    }

    headers.addAll(
      listOf(
        LoggerColumn.Custom("Average", averagePadding),
        LoggerColumn.Custom("V. Last", valuePadding),
        LoggerColumn.Custom("V. Avg.", valuePadding),
        LoggerColumn.Custom("History", 10),
      ),
    )

    Logger.header(*headers.toTypedArray())

    val players =
      viewModel.getAllPoolPreviewPlayers(
        teamId = teamId,
        name = name,
        minimal = minimal,
        current = current,
        sortValue = sortValue,
      )

    players.forEach { element ->
      val rows =
        mutableListOf(
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
          padding = currentPadding,
        ),
      )
//        }

      rows.addAll(
        listOf(
          LoggerColumn.Custom(
            Formatter.roundDouble(element.averagePoints),
            padding = averagePadding,
          ),
          LoggerColumn.Custom(element.poolValue, valuePadding),
          LoggerColumn.Custom(element.averagePoolValue, valuePadding),
        ),
      )
      val history =
        element
          .history
          .map { LoggerColumn.Custom(it, padding = 14) }
      rows.addAll(history)

      Logger.row(*rows.toTypedArray())
    }

    Logger.taskEnd()
  }
}
