#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
require "http"
Dir["./commands/*.rb"].each {|file| require file }
Dir["./helpers/*.rb"].each {|file| require file }
Dir["./services/*.rb"].each {|file| require file }

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

    desc "pool", "Show season information about pool data"
    def pool(season="20232024")
        initTask()
        PoolCommand
            .new(@dbService)
            .run(season)
    end

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
