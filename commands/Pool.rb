require_relative "../helpers/Logger"

class Pool < Thor
  desc "stats", "Get pool stats for a specific season"
  def stats(season = "20232024")
    initTask()

    Logger.taskTitle "Stats #{season}"

    players = @dbService.getPoolPlayersForSeason(season)

    PrintPlayerStatsLine("Nom","ID","G","G","A","P","Points")

    players.each do |player|
      stats = @dbService.getPoolPlayerStatsForSeason(player.id,"20222023")

      if stats != nil
        PrintPlayerStats(
          player,
          stats
        )
      else
        PrintPlayerStatsLine(player.fullName)
      end
    end

    Logger.taskEnd
  end

  no_tasks do
    def initTask()
      @dbService = DatabaseService.new
    end

    def PrintPlayerStats(player, stats)
      PrintPlayerStatsLine(
        player.fullName,
        player.id.to_s,
        stats.games.to_s,
        stats.goals.to_s,
        stats.assists.to_s,
        stats.points.to_s,
        stats.poolPoints(player.positionCode).to_s
      )
    end

    def PrintPlayerStatsLine(name, id = "0", games = "0", goals = "0", assists = "0", points = "0", pool = "0")
      Logger.info "#{name.rjust(30)}#{id.rjust(8)}     #{games.ljust(5)}#{goals.ljust(5)}#{assists.ljust(5)}#{points.ljust(5)}#{pool.ljust(5)}"
    end
  end
end
