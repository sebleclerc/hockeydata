#
# PlayerCommand
#

class PlayerCommand < BaseCommand
  desc "player playerId", "Show informations about a specific player."
    def info(playerId)
      initTask()

      Logger.taskTitle "Player for ID #{playerId}"

      player = @dbService.getPlayerForId(playerId)

      Logger.info "Name: #{player.fullName} (#{player.primaryNumber})"
      Logger.info "Birth #{player.birthDate} in #{player.birthLocation}"
      Logger.info ""
      Logger.info PlayerSeasonStats.formattedHeaderString()
      Logger.info ""

      stats = @dbService.getPlayerSeasonStatsForPlayerIdAndSeason(playerId)

      stats.each do |stat|
        Logger.info stat.formattedString(player.positionCode)
      end

      Logger.taskEnd()
    end

    default_task :info
end
