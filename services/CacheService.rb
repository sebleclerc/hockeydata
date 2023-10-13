require "http"

#
# Check local cache, if not present, fetch from web and save to json files
#

class CacheService
    def cachePositions
        Logger.debug "Cache positions"
        @filename = "positions.json"
        @endpoint = "/positions"

        checkCacheAndSave()
    end

    def cacheTeams
        Logger.debug "Cache teams"
        @filename = "teams.json"
        @endpoint = "/teams"

        checkCacheAndSave()
    end

    def cacheTeamsRoster(teams)
        Logger.debug "Cache roster for all teams"

        teams.each do |team|
           @filename = "teams-#{team.id}-roster.json"
           @endpoint = "/teams/#{team.id}/roster"

           checkCacheAndSave()
        end
    end

    def cachePlayerForId(id)
        Logger.debug "Cache player with ID #{id}"
        @filename = "#{id}-player.json"
        @endpoint = "/people/#{id}"

        checkCacheAndSave()
    end

    def cachePlayerArchiveStatsForId(id)
        Logger.debug "Fetching player's archive stats for ID #{id}"
        @filename = "#{id}-player-stats-archive.json"
        @endpoint = "/people/#{id}/stats?stats=yearByYear"

        checkCacheAndSave()
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

    def checkCacheAndSave
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
