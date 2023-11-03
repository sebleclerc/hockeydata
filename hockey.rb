#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
Dir["./commands/*.rb"].each {|file| require file }
Dir["./helpers/*.rb"].each {|file| require file }
Dir["./services/*.rb"].each {|file| require file }

class Hockey < Thor
    desc "hockey cache", "Fetch and cache files"
    subcommand "cache", CacheCommand

    desc "import", "Import JSON files in database"
    def import()
        initTask()
        ImportCommand
            .new(@importService, @dbService)
            .run()
    end

    desc "roster teamId", "Show team roster"
    def roster(teamId=nil)
        initTask()
        RosterCommand
            .new(@dbService)
            .run(teamId)
    end

    desc "player playerId", "Show informations about a specific player"
    def player(playerId)
        initTask()
        PlayerCommand
            .new(@dbService)
            .run(playerId)
    end

    desc "hockey pool", "All pool related tasks."
    subcommand "pool", PoolCommand

    desc "hockey salary", "All salary related tasks."
    subcommand "salary", SalaryCommand

    no_tasks do
        def initTask()
            @dbService = DatabaseService.new
            @cacheService = CacheService.new
            @importService = ImportService.new(@dbService)
        end
    end
end

Hockey.start(ARGV)
