require "http"

#
# Check local cache, if not present, fetch from web and save to json files
#

class CacheService
    def initialize
        @force = false
    end

    def cachePositions(force)
        Logger.debug "Cache positions"
        @force = force
        @filename = "positions.json"
        @endpoint = "/positions"

        deleteCacheIfNeeded()
        checkCacheAndSave()
    end

    def cacheTeams(force)
        Logger.debug "Cache teams"
        @force = force
        @filename = "teams.json"
        @endpoint = "/teams"

        deleteCacheIfNeeded()
        checkCacheAndSave()
    end

    def cacheTeamsRoster(teams, force)
        Logger.debug "Cache roster for all teams"
        @force = force

        teams.each do |team|
           @filename = "teams-#{team.id}-roster.json"
           @endpoint = "/teams/#{team.id}/roster"

           checkCacheAndSave()
        end
    end

    def cachePlayerForId(id, force)
        Logger.debug "Cache player with ID #{id}"
        @force = force
        @filename = "#{id}-player.json"
        @endpoint = "/people/#{id}"

        checkCacheAndSave()
    end

    def cachePlayerArchiveStatsForId(id, force)
        Logger.debug "Fetching player's archive stats for ID #{id}"
        @force = force
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

    def deleteCacheIfNeeded
        if @force && File.exist?(filePath)
            File.delete(filePath)
        end
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
