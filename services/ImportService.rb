#
# Import JSON files in database
#

require_relative("../helpers/Filenames.rb")

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
            roster = getCachedData()["roster"]
            @dbService.insertTeamRoster(team, roster)
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
            stat = getCachedData()["stats"][0]["splits"]
            @dbService.insertPlayerArchiveStat(id, stat)
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
