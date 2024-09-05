import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.HockeyData
import ca.sebleclerc.hockeydata.commands.CacheCommand
import ca.sebleclerc.hockeydata.commands.PoolCommand
import ca.sebleclerc.hockeydata.commands.TeamsCommand
import ca.sebleclerc.hockeydata.commands.subcommands.CachePlayerCommand
import ca.sebleclerc.hockeydata.commands.subcommands.CacheTeamCommand
import ca.sebleclerc.hockeydata.commands.subcommands.PoolPreviewCommand
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) = HockeyData()
  .subcommands(
    TeamsCommand(DI),
    CacheCommand(DI)
      .subcommands(
        CacheTeamCommand(DI),
        CachePlayerCommand(DI)
      ),
    PoolCommand(DI)
      .subcommands(
        PoolPreviewCommand(DI)
      )
  )
  .main(args)
