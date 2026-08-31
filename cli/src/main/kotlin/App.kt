import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.HockeyData
import ca.sebleclerc.hockeydata.cli.commands.CacheCommand
import ca.sebleclerc.hockeydata.cli.commands.PlayerCommand
import ca.sebleclerc.hockeydata.cli.commands.SalaryCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.CachePlayerCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.CacheTeamCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.CacheTeamsCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) {
  HockeyData()
    .subcommands(
      CacheCommand(DI)
        .subcommands(
          CacheTeamCommand(DI),
          CacheTeamsCommand(DI),
          CachePlayerCommand(DI),
        ),
      SalaryCommand(DI),
      PlayerCommand(DI),
    ).main(args)
}
