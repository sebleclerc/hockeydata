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
end
