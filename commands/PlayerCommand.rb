class PlayerCommand
  def initialize(dbService)
    @dbService = dbService
  end

  def run(playerId)
    Logger.taskTitle "Player for ID #{playerId}"

    player = @dbService.getPlayerForId(playerId)

    Logger.info "Name: #{player.fullName} (#{player.primaryNumber})"
    Logger.info "Birth #{player.birthDate} in #{player.birthLocation}"
    Logger.info ""
    Logger.info PlayerSeasonStats.formattedHeaderString()
    Logger.info ""

    stats = @dbService.getPlayerSeasonStatsForPlayerIdAndSeason(playerId)

    stats.each do |stat|
      Logger.info stat.formattedString()
    end

    Logger.info ""
    Logger.taskEnd()
  end
end
