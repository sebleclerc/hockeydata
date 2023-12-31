#
# PoolMeCommand
#

class PoolMeCommand < BaseCommand
  desc "me SEASON", "Getting pool data for me. Default to current season."
  def me(season=Constants.currentSeason)
    initTask()

    Logger.taskTitle "Stats #{season}"

    totalPoints = 0.0

    headers = [LoggerColumn.name()]
    headers.push(*PlayerSeasonStats.formattedHeaderRows())
    Logger.header(headers)

    forwards = @dbService.getPoolRosterPositionForSeason(season, 'F')

    forwards.each do |playerId|
      player = @dbService.getPlayerForId(playerId)
      statLine = [LoggerColumn.name(player.fullName)]

      stat = @dbService.getPlayerSeasonStatsForPlayerIdAndSeason(player.id, season).first

      if stat != nil
        totalPoints += stat.poolPoints(player.positionCode)
        statLine.push(*stat.formattedRows(player.positionCode))
      end

      Logger.row statLine
    end

    Logger.info ""

    header = "Name".showHeader()
    header += PlayerSeasonStatsGoaler.formattedHeaderString()

    Logger.info header

    goalers = @dbService.getPoolRosterPositionForSeason(season, 'G')

    goalers.each do |playerId|
      player = @dbService.getPlayerForId(playerId)
      stat = @dbService.getPlayerSeasonStatsGoalerForPlayerIdAndSeason(player.id, season).first

      statLine = player.fullName.show()

      if stat != nil
        totalPoints += stat.poolPoints()
        statLine += stat.formattedString()
      end

      Logger.info statLine
    end

    Logger.info ""
    Logger.info "Total points: #{totalPoints}"

    Logger.taskEnd
  end

  default_task :me
end
