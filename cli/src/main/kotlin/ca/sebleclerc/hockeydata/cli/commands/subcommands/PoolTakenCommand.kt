package ca.sebleclerc.hockeydata.cli.commands.subcommands

import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.commands.BaseCommand
import ca.sebleclerc.hockeydata.cli.helpers.Logger
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

class PoolTakenCommand(
  di: DI,
) : BaseCommand(di, name = "taken") {
  private val playerId by argument().int()

  override fun run() {
    super.run()

    Logger.taskTitle("Adding player $playerId to the taken list")

    di.database.updatePlayerForPool(playerId, PoolDraftStatut.TAKEN)

    Logger.completed()
    Logger.taskEnd()
  }
}
