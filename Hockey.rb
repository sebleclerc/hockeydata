#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
require "http"
require "./commands/CacheCommand"
require "./commands/ImportCommand"
require "./commands/PlayerCommand"
require "./commands/RosterCommand"
require "./commands/Pool"
require "./commands/Salary"
require "./helpers/Logger"
require "./helpers/PoolRoster"
require "./services/CacheService"
require "./services/DatabaseService"
require "./services/ImportService"

class Hockey < Thor
    desc "cache", "Fetch and cache files"
    def cache(type = "all", force = false)
        initTask()
        CacheCommand
          .new(@cacheService, @dbService)
          .run(type, force)
    end

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

    desc "pool SEASON", "Some Parent Command"
    subcommand "pool", Pool

    desc "salary season", "Some"
    subcommand "salary", Salary

    no_tasks do
        def initTask()
            @dbService = DatabaseService.new
            @cacheService = CacheService.new
            @importService = ImportService.new(@dbService)
        end
    end
end

Hockey.start(ARGV)
