import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.HockeyData
import ca.sebleclerc.hockeydata.commands.TeamsCommand
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) = HockeyData()
  .subcommands(TeamsCommand(DI))
  .main(args)
