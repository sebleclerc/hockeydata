#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
require "http"
require "./services/DataService"

class Hockey < Thor
    desc "hockey build", "Build local data"
    def build()
        initTask()

        p "Building and updating data..."

        # @dataService.updatePositions
        # @dataService.updateTeams

        rosterPlayerIds = [
            8478402 # Connor McDavid
        ]
        @dataService.updatePlayers(rosterPlayerIds)
    end

    no_tasks do
        def initTask()
            @dataService = DataService.new
        end
    end
end

Hockey.start(ARGV)