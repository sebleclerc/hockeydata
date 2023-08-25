#
# Import JSON files in database
#

class ImportService
    def initialize(dbService, localService)
        @dbService = dbService
        @localService = localService
    end

    def importPositions
        positions = @localService.getAllPositions
        @dbService.insertPositions(positions)
    end

    def importTeams
        teams = @localService.getAllTeams
        @dbService.insertTeams(teams)
    end

    def importTeamsRoster
        teams = @dbService.getAllTeams()

        @dbService.clearTeamPlayers()

        teams.each do |team|
            roster = @localService.getTeamRoster(team)
            @dbService.insertTeamRoster(team, roster)
        end
    end

    def importPlayers(playerIds)
        playerIds.each do |playerId|
            player = @localService.getPlayerForId(playerId)
            @dbService.insertPlayer(player)
        end
    end

    def importPlayerArchiveStats(playerIds)
        playerIds.each do |playerId|
            stat = @localService.getPlayerArchiveStatsForId(playerId)
            @dbService.insertPlayerArchiveStat(playerId, stat)
        end
    end
end
