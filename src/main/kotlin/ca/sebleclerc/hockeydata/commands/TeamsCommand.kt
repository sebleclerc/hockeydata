package ca.sebleclerc.hockeydata.commands

import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.services.DatabaseService
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument

class TeamsCommand : CliktCommand(name = "teams") {
  private val option: String by argument()

  override fun run() {
    Logger.taskTitle("Teams with option $option")

    Logger.header(
      LoggerColumn.ID(),
      LoggerColumn.Name()
    )

    val service = DatabaseService()
    service.getAllTeams().forEach {
      Logger.row(
        LoggerColumn.ID(it.id),
        LoggerColumn.Name(it.name)
      )
    }

    Logger.taskEnd()
  }
}
