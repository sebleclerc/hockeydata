require "http"

#
# Check local cache, if not present, fetch from web
#

class LocalService
    def validateEverything(teams, playerIds, forceUpdate)
        Logger.info "Update everything"
        validateAllPositions
        validateAllTeams
        validateAllTeamsRoster(teams)

        playerIds.each do |playerId|
            validatePlayerForId(playerId)
            validatePlayerArchiveStatsForId(playerId)
        end
    end

    def validateAllPositions
        Logger.debug "Validating all positions"
        @filename = "positions.json"
        @endpoint = "/positions"

        checkCacheAndUpdate()
    end

    def getAllPositions
        Logger.debug "Get all positions"
        @filename = "positions.json"

        return getCachedData()
    end
#
    def validateAllTeams
        Logger.debug "Validating all teams"
        @filename = "teams.json"
        @endpoint = "/teams"

        checkCacheAndUpdate()
    end

    def getAllTeams
        Logger.debug "Get all Teams"
        @filename = "teams.json"

        return getCachedData()["teams"]
    end

    def validateAllTeamsRoster(teams)
        Logger.debug "Validating roster for all teams"

        teams.each do |team|
           @filename = "teams-#{team.id}-roster.json"
           @endpoint = "/teams/#{team.id}/roster"

           checkCacheAndUpdate()
        end
    end

    def getTeamRoster(team)
        @filename = "teams-#{team.id}-roster.json"

        return getCachedData()["roster"]
    end

    def validatePlayerForId(id)
        Logger.debug "Validate player with ID #{id}"
        @filename = "#{id}-player.json"
        @endpoint = "/people/#{id}"

        checkCacheAndUpdate()
    end

    def getPlayerForId(id)
        Logger.debug "Get player with ID #{id}"
        @filename = "#{id}-player.json"

        return getCachedData()["people"][0]
    end

    def validatePlayerArchiveStatsForId(id)
        Logger.debug "Fetching player's archive stats for ID #{id}"
        @filename = "#{id}-player-stats-archive.json"
        @endpoint = "/people/#{id}/stats?stats=yearByYear"

        checkCacheAndUpdate()
    end

    def getPlayerArchiveStatsForId(id)
        Logger.debug "Fetching player's archive stats for ID #{id}"
        @filename = "#{id}-player-stats-archive.json"

        return getCachedData()["stats"][0]["splits"]
    end

    private

    def filePath
        return "./json/#{@filename}"
    end

    def getCachedData
        content = File.read(filePath)
        return JSON.parse(content)
    end

    def apiEndpoint
        return "https://statsapi.web.nhl.com/api/v1" + @endpoint
    end

    def checkCacheAndUpdate
        if File.exists?(filePath)
            Logger.debug "Cache file exist, nothing to do"
        else
            Logger.warning "Calling NHL's API"
            Logger.debug apiEndpoint
            response = HTTP.get(apiEndpoint)
            File.write(filePath, response)
        end
    end
end
