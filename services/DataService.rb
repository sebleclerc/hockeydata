require_relative "LocalService.rb"
require_relative "DatabaseService.rb"

class DataService
    def initialize(dbService, localService)
        @dbService = dbService
        @localService = localService
    end

    def updatePositions
        positions = @apiService.getAllPositions
        @dbService.insertPositions(positions)
    end

    def updateTeams
        teams = @apiService.getAllTeams
        @dbService.insertTeams(teams)
    end

    def updatePlayers(playerIds)
        playerIds.each do |playerId|
            player = @apiService.getPlayerForId(playerId)
            @dbService.insertPlayer(player)
        end
    end
end