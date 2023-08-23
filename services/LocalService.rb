require "http"

#
# Check local cache, if not present, fetch from web
#

class LocalService
    def validateEverything(playerIds, forceUpdate)
        Logger.info "Update everything"
        validateAllPositions
        validateAllTeams

        playerIds.each do |playerId|
            validatePlayerForId(playerId)
            validatePlayerArchiveStatsForId(playerId)
        end
    end

    def validateAllPositions
        Logger.info "Validating all positions"
        @filename = "positions.json"
        @endpoint = "/positions"

        checkCacheAndUpdate()
    end

    def getAllPositions
        Logger.info "Get all positions"
        @filename = "positions.json"

        return getCachedData()
    end
#
    def validateAllTeams
        Logger.info "Validating all teams"
        @filename = "teams.json"
        @endpoint = "/teams"

        checkCacheAndUpdate()
    end

    def getAllTeams
        Logger.info "Get all Teams"
        @filename = "teams.json"

        return getCachedData()["teams"]
    end

    def validatePlayerForId(id)
        Logger.info "Validate player with ID #{id}"
        @filename = "#{id}-player.json"
        @endpoint = "/people/#{id}"

        checkCacheAndUpdate()
    end

    def getPlayerForId(id)
        Logger.info "Get player with ID #{id}"
        @filename = "#{id}-player.json"

        return getCachedData()["people"][0]
    end

    def validatePlayerArchiveStatsForId(id)
        Logger.info "Fetching player's archive stats for ID #{id}"
        @filename = "#{id}-player-stats-archive.json"
        @endpoint = "/people/#{id}/stats?stats=yearByYear"

        checkCacheAndUpdate()
    end

    def getPlayerArchiveStatsForId(id)
        Logger.info "Fetching player's archive stats for ID #{id}"
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
