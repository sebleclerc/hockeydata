import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.HockeyData
import ca.sebleclerc.hockeydata.commands.CacheCommand
import ca.sebleclerc.hockeydata.commands.PlayerCommand
import ca.sebleclerc.hockeydata.commands.PoolCommand
import ca.sebleclerc.hockeydata.commands.SalaryCommand
import ca.sebleclerc.hockeydata.commands.TeamsCommand
import ca.sebleclerc.hockeydata.commands.subcommands.CachePlayerCommand
import ca.sebleclerc.hockeydata.commands.subcommands.CacheTeamCommand
import ca.sebleclerc.hockeydata.commands.subcommands.CacheTeamsCommand
import ca.sebleclerc.hockeydata.commands.subcommands.PoolPreviewCommand
import ca.sebleclerc.hockeydata.commands.subcommands.PoolTakenCommand
import ca.sebleclerc.hockeydata.commands.subcommands.SalaryAllCommand
import ca.sebleclerc.hockeydata.commands.subcommands.SalaryTeamCommand
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) = HockeyData()
  .subcommands(
    TeamsCommand(DI),
    CacheCommand(DI)
      .subcommands(
        CacheTeamCommand(DI),
        CacheTeamsCommand(DI),
        CachePlayerCommand(DI)
      ),
    PoolCommand(DI)
      .subcommands(
        PoolPreviewCommand(DI),
        PoolTakenCommand(DI)
      ),
    SalaryCommand(DI)
      .subcommands(
        SalaryTeamCommand(DI),
        SalaryAllCommand(DI)
      ),
    PlayerCommand(DI)
  )
  .main(args)
