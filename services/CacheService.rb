#
# Check local cache, if not present, fetch from web and save to json files
#

require "http"

class CacheService
    def cachePositions(force)
        Logger.debug "Cache positions"

        @filename = Filenames.positions()
        @endpoint = "/positions"

        deleteCacheIfNeeded(force)
        checkCacheAndSave()
    end

    def cacheTeams(force)
        Logger.debug "Cache teams"

        @filename = Filenames.teams()
        @endpoint = "/teams"

        deleteCacheIfNeeded(force)
        checkCacheAndSave()
    end

    def cacheTeamRoster(team, force)
        Logger.debug "Cache roster for team #{team.name}"

        @filename = Filenames.teamRoster(team)
        @endpoint = "/teams/#{team.id}/roster"

        deleteCacheIfNeeded(force)
        checkCacheAndSave()
    end

    def cachePlayerForId(id, force)
        Logger.debug "Cache player with ID #{id}"

        @filename = Filenames.playerForId(id)
        @endpoint = "/people/#{id}"

        deleteCacheIfNeeded(force)
        checkCacheAndSave()
    end

    def cachePlayerArchiveStatsForId(id, force)
        Logger.debug "Fetching player's archive stats for ID #{id}"

        @filename = Filenames.playerArchiveStatsForId(id)
        @endpoint = "/people/#{id}/stats?stats=yearByYear"

        deleteCacheIfNeeded(force)
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

    def deleteCacheIfNeeded(force)
        if force && File.exist?(filePath)
            Logger.info "Deleting file at path #{filePath}"
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
