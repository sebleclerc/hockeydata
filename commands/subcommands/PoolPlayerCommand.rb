#
# PoolPlayerCommand
#

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
