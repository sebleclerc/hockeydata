require "http"

class APIService
    def getAllPositions
        Logger.info "Fetching all positions"

        response = HTTP.get("https://statsapi.web.nhl.com/api/v1/positions")
        return response.parse
    end
#
    def getAllTeams
        Logger.info "Fetching all teams"

        response = HTTP.get("https://statsapi.web.nhl.com/api/v1/teams")
        return response.parse["teams"]
    end

    def getPlayerForId(id)
        Logger.info "Fetching player with ID #{id}"

        response = HTTP.get("https://statsapi.web.nhl.com/api/v1/people/#{id}")
        return response.parse["people"][0]
    end
end