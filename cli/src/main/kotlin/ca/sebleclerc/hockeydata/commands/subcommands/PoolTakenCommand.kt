package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.models.PoolDraftStatut
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

class PoolTakenCommand(di: DI) : BaseCommand(di, name = "taken") {
  private val playerId by argument().int()

  override fun run() {
    super.run()

    Logger.taskTitle("Adding player $playerId to the taken list")

    di.database.updatePlayerForPool(playerId, PoolDraftStatut.TAKEN)

    Logger.completed()
    Logger.taskEnd()
  }
}