import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.HockeyData
import ca.sebleclerc.hockeydata.cli.commands.PlayerCommand
import ca.sebleclerc.hockeydata.cli.commands.SalaryCommand
import ca.sebleclerc.hockeydata.cli.commands.CacheCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) {
  HockeyData()
    .subcommands(
      CacheCommand(DI)
        .subcommands(
          CacheCommand(DI),
        ),
      SalaryCommand(DI),
      PlayerCommand(DI),
    ).main(args)
}
