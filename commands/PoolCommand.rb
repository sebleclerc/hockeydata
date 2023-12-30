#
# PoolCommand
#

class PoolPreviewCommand < BaseCommand
  desc "all", "Show preview for all players."
  def all()
    initTask()

    Logger.taskTitle "Preview stats for season #{Constants.currentSeason}"

    poolPlayers = getAvailablePlayers()
    printListOfPlayers(poolPlayers)

    Logger.taskEnd
  end

  desc "forwards", "Show preview only for forwards."
  def forwards()
    initTask()

    Logger.taskTitle "Preview stats for season #{Constants.currentSeason}"

    forwards = getAvailablePlayers()
                .select { |player| ['C','L','R'].include? player.player.positionCode }
    printListOfPlayers(forwards)

    Logger.taskEnd
  end

  desc "defenses", "Show preview only for forwards."
  def defenses()
    initTask()

    Logger.taskTitle "Preview stats for season #{Constants.currentSeason}"

    defenses = getAvailablePlayers()
                .select { |player| player.player.positionCode == 'D' }
    printListOfPlayers(defenses)

    Logger.taskEnd
  end

  default_task :all

  no_tasks do
    def getAvailablePlayers()
      return @dbService
              .getAvailablePlayerStatsSalaryForSeason(Constants.currentSeason)
              .select { |player| player.stat.poolPoints(player.player.positionCode) > 10 }
    end

    def printListOfPlayers(players)
      header = Player.showFullNameHeader
      header += "ID".rjust(8).colorize(:yellow)
      header += PlayerSalarySeason.showAVVHeader
      header += "P".intHeader()
      header += "Pool".intHeader()
      header += "Value".floatHeader()
      Logger.info header

      players.each do |player|
        statLine = player.player.showFullName
        statLine += player.player.id.to_s.rjust(8)
        statLine += player.salary.showAVV
        statLine += player.stat.points.show()
        statLine += player.poolPoints().show()

        statLine += player.value.show()

        Logger.info statLine
      end
    end
  end
end

class PoolPlayerCommand < BaseCommand
  desc "add playerId", "Add playerId to list of taken players"
  def add(playerId)
    initTask()

    Logger.taskTitle "Adding player #{playerId} to the taken list"

    @dbService.addPlayerStatutTaken(playerId, Constants.currentSeason)

    Logger.completed
    Logger.taskEnd
  end

  desc "rem playerId", "Remove playerId from the list of taken players"
  def rem(playerId)
    initTask()

    Logger.taskTitle "Removing player #{playerId} to the taken list"

    @dbService.deletePlayerStatutTaken(playerId, Constants.currentSeason)

    Logger.completed
    Logger.taskEnd
  end

  desc "select playerId points", "Select playerId to the pool selection"
  def select(playerId, poolPoints)
    Logger.taskTitle "Select #{playerId} to the pool selection with #{poolPoints} points."

    initTask()

    player = @dbService.getPlayerForId(playerId)
    now = DateTime.now

    @dbService.selectPlayerForPool(
      playerId,
      Constants.currentSeason,
      "#{now.year}-#{now.month}-#{now.day}",
      poolPoints
    )

    Logger.completed
    Logger.taskEnd
  end

  desc "remoke playerId", "Revoke playerId from current selection."
  def revoke(playerId)
    Logger.taskTitle "Revoking player #{playerId} from current selection."

    initTask()

    player = @dbService.getPlayerForId(playerId)
    stat = @dbService.getPlayerSeasonStatsForPlayerIdAndSeason(player.id, Constants.currentSeason).first
    currentPoolPoints = stat.poolPoints(player.positionCode)
    now = DateTime.now

    @dbService.revokePlayerForPool(
      playerId,
      Constants.currentSeason,
      "#{now.year}-#{now.month}-#{now.day}",
      currentPoolPoints
    )

    Logger.completed
    Logger.taskEnd
  end
end

class PoolCommand < BaseCommand
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
  subcommand "preview", PoolPreviewCommand

  desc "player", "Related to specific player"
  subcommand "player", PoolPlayerCommand
end
