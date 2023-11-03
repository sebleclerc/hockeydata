#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
Dir["./commands/*.rb"].each {|file| require file }
Dir["./helpers/*.rb"].each {|file| require file }
Dir["./services/*.rb"].each {|file| require file }

class Hockey < Thor
    desc "hockey cache", "Fetch and cache files"
    subcommand "cache", CacheCommand

    desc "import", "Import JSON files into database."
    def import()
        dbService = DatabaseService.new
        importService = ImportService.new(dbService)

        Logger.taskTitle "Importing cached files"

        Logger.info "Import positions"
        importService.importPositions()

        Logger.info "Import teams + roster"
        importService.importTeams()
        importService.importTeamRosters()

        Logger.info "Import pool players + archive stats"
        players = dbService.getAllRosters()

        players.each do |playerId|
            importService.importPlayerForId(playerId)
            importService.importPlayerArchiveStatsForId(playerId)
        end

        Logger.taskEnd()
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
