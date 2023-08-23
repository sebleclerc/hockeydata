require "http"

class APIService
    def getAllPositions
        Logger.info "Fetching all positions"
        @filename = "positions.json"
        @endpoint = "/positions"

        return callApiEndpoint()
    end
#
    def getAllTeams
        Logger.info "Fetching all teams"
        @filename = "teams.json"
        @endpoint = "/teams"

        return callApiEndpoint()["teams"]
    end

    def getPlayerForId(id)
        Logger.info "Fetching player with ID #{id}"
        @filename = "#{id}-player.json"
        @endpoint = "/people/#{id}"

        return callApiEndpoint()["people"][0]
    end

    def getPlayerArchiveStatsForId(id)
        Logger.info "Fetching player's archive stats for ID #{id}"
        @filename = "#{id}-player-stats-archive.json"
        @endpoint = "/people/#{id}/stats?stats=yearByYear"
        
        return callApiEndpoint()["stats"]["splits"]
    end

    private 

    def filePath
        return "./json/#{@filename}"
    end

    def apiEndpoint
        return "https://statsapi.web.nhl.com/api/v1" + @endpoint
    end

    def callApiEndpoint
        if File.exists?(filePath)
            Logger.debug "Loading from cache"
            content = File.read(filePath)
            return JSON.parse(content)
        else
            Logger.warning "Calling NHL's API"
            Logger.debug apiEndpoint
            response = HTTP.get(apiEndpoint)
            File.write(filePath, response)

            return response.parse
        end
    end
end
