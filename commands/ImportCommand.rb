class ImportCommand
  def initialize(importService)
    @importService = importService
  end

  def run
    Logger.taskTitle "Task Import"

    Logger.info "Import positions"
    @importService.importPositions()

    Logger.info "Import teams + roster"
    @importService.importTeams()
    @importService.importTeamRosters()

    Logger.info "Import pool players + archive stats"
    poolRoster = PoolRoster.rosterPlayerIds

    poolRoster.each do |playerId|
      @importService.importPlayerForId(playerId)
      @importService.importPlayerArchiveStatsForId(playerId)
    end

    Logger.taskEnd()
  end
end
