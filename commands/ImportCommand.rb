#
# ImportCommand
#

class ImportCommand < BaseCommand
  desc "import", "Import JSON files into database."
  def import()
    initTask()

    Logger.taskTitle "Importing cached files"

    Logger.info "Import positions"
    @importService.importPositions()

    Logger.info "Import teams + roster"
    @importService.importTeams()
    @importService.importTeamRosters()

    Logger.info "Import pool players + archive stats"
    players = @dbService.getAllPlayers()

    players.each do |playerId|
      @importService.importPlayerForId(playerId)
      # @importService.importPlayerArchiveStatsForId(playerId)
    end

    Logger.taskEnd()
  end

  default_task :import
end
