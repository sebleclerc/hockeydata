#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
require "http"
require "./commands/CacheCommand"
require "./commands/ImportCommand"
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
            .new(@importService)
            .run()
    end

    desc "local", "Fetch and save local data"
    def local()
        Logger.taskTitle "Task Local"
        initTask()

        Logger.info "Building local data..."

        teams = @dbService.getAllTeams()
        # @localService.validateEverything(teams, PoolRoster.rosterPlayerIds, false)

        Logger.taskEnd()
    end

    desc "roster", "Fetch, validate and import players for a Team"
    def roster(teamId)
        Logger.taskTitle "Task roster #{teamId}"
        initTask()

        team = @dbService.getTeamForId(teamId)
        Logger.info "Roster for team #{team.name}"

        roster = @dbService.getTeamRoster(team)
        roster.each do |playerId|
            @localService.validatePlayerForId(playerId)
        end

        Logger.info "Importing players"
        @importService.importPlayers(roster)

        Logger.taskEnd()
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
