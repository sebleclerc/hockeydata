#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
require "http"
require "./services/DataService"
require "./services/DatabaseService"
require "./services/LocalService"
require "./helpers/Logger"

class Hockey < Thor
    desc "local", "Fetch and save local data"
    def local()
        Logger.info "Task Local"
        initTask()

        Logger.debug "Building local data..."

        @localService.updateEverything(rosterPlayerIds)
    end

    no_tasks do
        def initTask()
            @dbService = DatabaseService.new
            @localService = LocalService.new
            @dataService = DataService.new(@dbService, @localService)
        end

        def rosterPlayerIds
            return [
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

                # Prospect
                8481533, # Trevor Zegras
            ]
        end
    end
end

Hockey.start(ARGV)