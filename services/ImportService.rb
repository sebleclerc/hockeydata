#
# Import JSON files in database
#

class ImportService
    def initialize(dbService)
        @dbService = dbService
    end

    def importPositions
        @filename = Filenames.positions()
        positions = getCachedData()
        @dbService.insertPositions(positions)
    end

    def importTeams
        @filename = Filenames.teams()
        teams = getCachedData()["teams"]
        @dbService.insertTeams(teams)
    end

    def importTeamRosters
        # Getting all teams
        @filename = Filenames.teams()
        teams = getCachedData()["teams"]

        # Flush current data
        @dbService.clearTeamPlayers()

        # And then import rosters
        teams.each do |jTeam|
            team = Team.fromJson(jTeam)
            @filename = Filenames.teamRoster(team)

            cachedFile = getCachedData()

            @dbService.insertTeamRoster(team, cachedFile["forwards"])
            @dbService.insertTeamRoster(team, cachedFile["defensemen"])
            @dbService.insertTeamRoster(team, cachedFile["goalies"])
        end
    end

    def importPlayerForId(id)
        @filename = Filenames.playerForId(id)

        if File.exist?(filePath)
            player = getCachedData()["people"][0]
            @dbService.insertPlayer(player)
        end
    end

    def importPlayerArchiveStatsForId(id)
        @filename = Filenames.playerArchiveStatsForId(id)

        if File.exist?(filePath)
            player = @dbService.getPlayerForId(id)
            stat = getCachedData()["stats"][0]["splits"]

            if player.positionCode == 'G'
                @dbService.insertPlayerArchiveStatGoaler(id, stat)
            else
                @dbService.insertPlayerArchiveStat(id, stat)
            end
        end
    end

    private

    def filePath
        return "./json/#{@filename}"
    end


    def getCachedData
        content = File.read(filePath)
        return JSON.parse(content)
    end
end
