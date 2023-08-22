#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
require "http"
require "./services/DatabaseService"

class Hockey < Thor
    desc "hockey build", "Build local data"
    def build()
        initTask()

        p "Building data..."

        # response = HTTP.get("https://statsapi.web.nhl.com/api/v1/positions")
        # @dbService.insertPositions(response.parse)

        # tResponse = HTTP.get("https://statsapi.web.nhl.com/api/v1/teams")
        # @dbService.insertTeams(tResponse.parse["teams"])
    end

    no_tasks do
        def initTask()
            @dbService = DatabaseService.new
        end
    end
end

Hockey.start(ARGV)