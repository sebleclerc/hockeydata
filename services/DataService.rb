require_relative "APIService"
require_relative "DatabaseService"

class DataService
    def initialize
        @dbService = DatabaseService.new
        @apiService = APIService.new
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