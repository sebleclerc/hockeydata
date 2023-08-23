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
            8478402, # Connor McDavid
            8473419, # Brad Marchand
            8474564, # Steven Stamkos
            8479343, # Clayton Keller
            8476539, # Jonathan Marchesseault
            8478864, # Kirill Kaprizov
            8484144, # Connor Bedard
            8482117, # Lukas Reichel
            8477451, # Ryan Hartman
            8481093, # RHP
            8482745, # Mason McTavish
            8483524, # Share Wrigth
            8479410, # Sergachev
            8474590, # John Carlson
            8482803, # Olen Zellweger
            8482730, # Brandt Clark
            8483460, # David Jiricek
            8483495, # Simon Nemec
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