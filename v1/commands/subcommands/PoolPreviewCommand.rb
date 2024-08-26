#
# PoolPreviewCommand
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
      Logger.header([
        LoggerColumn.name(),
        LoggerColumn.id(),
        LoggerColumn.avv(),
        LoggerColumn.int("P"),
        LoggerColumn.float("Pool"),
        LoggerColumn.poolValue()
      ])

      players.each do |player|
        Logger.row([
          LoggerColumn.name(player.player.showFullName),
          LoggerColumn.id(player.player.id),
          LoggerColumn.avv(player.salary),
          LoggerColumn.int("P", player.stat.points),
          LoggerColumn.float("Pool", player.poolPoints()),
          LoggerColumn.poolValue(player.value)
        ])
      end
    end
  end
end
