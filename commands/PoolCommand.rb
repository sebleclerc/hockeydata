#
# PoolCommand
#

class PoolCommand < BaseCommand
  desc "me SEASON", "Getting pool data for me. Default to current season."
  def me(season=Constants.currentSeason)
    initTask()

    Logger.taskTitle "Stats #{season}"

    totalPoints = 0.0

    header = "Name".showHeader()
    header += PlayerSeasonStats.formattedHeaderString()
    Logger.info header

    forwards = @dbService.getPoolRosterPositionForSeason(season, 'F')

    forwards.each do |playerId|
      player = @dbService.getPlayerForId(playerId)
      statLine = player.fullName.show()

      stat = @dbService.getPlayerSeasonStatsForPlayerIdAndSeason(player.id, season).first

      if stat != nil
        totalPoints += stat.poolPoints(player.positionCode)
        statLine += stat.formattedString(player.positionCode)
      end

      Logger.info statLine
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

  desc "preview", "Trying to find interesting pool choices."
  def preview()
    initTask()

    Logger.taskTitle "Preview stats for season #{Constants.currentSeason}"

    poolPlayers = @dbService.getAvailablePlayerStatsSalaryForSeason(Constants.currentSeason)

    header = Player.showFullNameHeader
    header += PlayerSalarySeason.showAVVHeader
    header += "P".intHeader()
    header += "Pool".intHeader()
    header += "Value".floatHeader()
    Logger.info header

    poolPlayers.each do |player|
      statLine = player.player.showFullName
      statLine += player.salary.showAVV
      statLine += player.stat.points.show()
      statLine += player.poolPoints().show()

      statLine += player.value.show()

      Logger.info statLine
    end

    Logger.taskEnd
  end
end
