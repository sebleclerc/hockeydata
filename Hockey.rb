#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
require "http"
require "./services/DataService"
require "./helpers/Logger"

class Hockey < Thor
    desc "hockey build", "Build local data"
    def build()
        Logger.info "Task Build"
        initTask()

        Logger.debug "Building and updating data..."

        @dataService.updatePositions
        @dataService.updateTeams

        rosterPlayerIds = [
            8478402 # Connor McDavid
        ]
        
        @dataService.updatePlayers(rosterPlayerIds)
        @dataService.updatePlayerArchiveStats(rosterPlayerIds)
    end

    no_tasks do
        def initTask()
            @dataService = DataService.new
        end
    end
end

Hockey.start(ARGV)