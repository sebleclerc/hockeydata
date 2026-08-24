import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.HockeyData
import ca.sebleclerc.hockeydata.cli.commands.CacheCommand
import ca.sebleclerc.hockeydata.cli.commands.PlayerCommand
import ca.sebleclerc.hockeydata.cli.commands.PoolCommand
import ca.sebleclerc.hockeydata.cli.commands.SalaryCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.CachePlayerCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.CacheTeamCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.CacheTeamsCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.PoolPreviewCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.PoolTakenCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.SalaryAllCommand
import ca.sebleclerc.hockeydata.cli.commands.subcommands.SalaryTeamCommand
import ca.sebleclerc.hockeydata.commands.subcommands.PoolMeCommand
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
      PoolCommand(DI)
        .subcommands(
          PoolPreviewCommand(DI),
          PoolTakenCommand(DI),
          PoolMeCommand(DI),
        ),
      SalaryCommand(DI)
        .subcommands(
          SalaryTeamCommand(DI),
          SalaryAllCommand(DI),
        ),
      PlayerCommand(DI),
    ).main(args)
}
