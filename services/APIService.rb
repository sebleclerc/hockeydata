require "http"

class APIService
    def getAllPositions
        response = HTTP.get("https://statsapi.web.nhl.com/api/v1/positions")
        return response.parse
    end
#
    def getAllTeams
        response = HTTP.get("https://statsapi.web.nhl.com/api/v1/teams")
        return response.parse["teams"]
    end

    def getPlayerForId(id)
        response = HTTP.get("https://statsapi.web.nhl.com/api/v1/people/#{id}")
        return response.parse["people"][0]
    end
end