class PoolCommand
  def initialize(dbService)
    @dbService = dbService
  end

  def run(season)
    Logger.taskTitle "Stats #{season}"
    Logger.info ""

    totalPoints = 0.0
    players = @dbService.getPoolPlayersForSeason(season)

    header = "Name".showHeader()
    header += PlayerSeasonStats.formattedHeaderString()
    Logger.info header

    players.each do |player|
      statLine = player.fullName.show()

      stat = @dbService.getPlayerSeasonStatsForPlayerIdAndSeason(player.id, season).first

      if stat != nil
        totalPoints += stat.poolPoints(player.positionCode)
        statLine += stat.formattedString(player.positionCode)
      end

      Logger.info statLine
    end

    Logger.info ""

    Logger.info "Total points: #{totalPoints}"

    Logger.info ""
    Logger.taskEnd
  end

  private

  def printStats()
  end

  # desc "stats", "Get pool stats for a specific season"
  # def stats(season = "20232024")

  #   PrintPlayerStatsLine("Nom","ID","G","G","A","P","Pool","PG","PA","PP")

  #   players.each do |player|
  #     # change call
  #     #stats = @dbService.getPoolPlayerStatsForSeason(player.id,"20222023")

  #     if stats != nil
  #       PrintPlayerStats(
  #         player,
  #         stats
  #       )
  #     else
  #       PrintPlayerStatsLine(player.fullName)
  #     end
  #   end
  # end

  # no_tasks do
  #   def initTask()
  #     @dbService = DatabaseService.new
  #   end

  #   def PrintPlayerStats(player, stats)
  #     PrintPlayerStatsLine(
  #       player.fullName,
  #       player.id.to_s,
  #       stats.games.to_s,
  #       stats.goals.to_s,
  #       stats.assists.to_s,
  #       stats.points.to_s,
  #       stats.poolPoints(player.positionCode).to_s,
  #       stats.projectedGoals.to_s,
  #       stats.projectedAssists.to_s,
  #       stats.projectedPoolPoints(player.positionCode).to_s
  #     )
  #   end

  #   def PrintPlayerStatsLine(name, id = "0", games = "0", goals = "0", assists = "0", points = "0", pool = "0", pGoals = "0", pAssists = "0", pPool = "0")
  #     Logger.info "#{name.rjust(30)}#{id.rjust(8)}     #{games.ljust(5)}#{goals.ljust(5)}#{assists.ljust(5)}#{points.ljust(5)}#{pool.ljust(6)}#{pGoals.ljust(6)}#{pAssists.ljust(6)}#{pPool.ljust(5)}"
  #   end
  # end
end
