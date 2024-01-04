#
# PoolMeCommand
#

class PoolMeCommand < BaseCommand
  desc "me SEASON", "Getting pool data for me. Default to current season."
  def me(season=Constants.currentSeason)
    initTask()
    @season = season

    Logger.taskTitle "Stats #{season}"

    headers = [LoggerColumn.name()]
    headers.push(*PlayerSeasonStats.formattedHeaderRows())
    Logger.header(headers)

    calculateForwards(PoolDraftStatut::SELECTED)
    calculateForwards(PoolDraftStatut::EXCHANGED)

    headers = [LoggerColumn.name()]
    headers.push(*PlayerSeasonStatsGoaler.formattedHeaderRows())
    Logger.header(headers)

    goalers = @dbService.getPoolRoster(season, 'G', [PoolDraftStatut::SELECTED, PoolDraftStatut::EXCHANGED])

    goalers.each do |playerId|
      player = @dbService.getPlayerForId(playerId)
      stat = @dbService.getPlayerSeasonStatsGoalerForPlayerIdAndSeason(player.id, season).first

      statLine = [LoggerColumn.name(player.fullName)]

      if stat != nil
        @totalPoints += stat.poolPoints()
        statLine.push(*stat.formattedRows())
      end

      Logger.row statLine
    end

    Logger.info ""
    Logger.info "Total points: #{@totalPoints}"

    Logger.taskEnd
  end

  default_task :me

  no_tasks do
    def initTask()
      super
      @totalPoints = 0.0
    end

    def calculateForwards(statut)
      forwards = @dbService.getPoolRoster(@season, 'F', statut)

      forwards.each do |playerId|
        player = @dbService.getPlayerForId(playerId)
        statLine = [LoggerColumn.name(player.fullName)]

        stat = @dbService.getPlayerSeasonStatsForPlayerIdAndSeason(player.id, @season).first

        if stat != nil
          @totalPoints += stat.poolPoints(player.positionCode)
          statLine.push(*stat.formattedRows(player.positionCode))
        end

      Logger.row statLine
    end

    Logger.info ""
    end
  end
end
