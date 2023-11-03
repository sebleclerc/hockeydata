#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby
#
# Hockey base app
#

require "thor"
Dir["./commands/*.rb"].sort.each {|file| require file }
Dir["./helpers/*.rb"].each {|file| require file }
Dir["./services/*.rb"].each {|file| require file }

class Hockey < Thor
    desc "hockey cache", "Fetch and cache files"
    subcommand "cache", CacheCommand

    desc "import", "Import JSON files into databse."
    subcommand "import", ImportCommand

    desc "roster [teamId]", "Team related commands."
    subcommand "roster", RosterCommand

    desc "player playerId", "Show informations about a specific player."
    def player(playerId)
        dbService = DatabaseService.new

        Logger.taskTitle "Player for ID #{playerId}"

        player = dbService.getPlayerForId(playerId)

        Logger.info "Name: #{player.fullName} (#{player.primaryNumber})"
        Logger.info "Birth #{player.birthDate} in #{player.birthLocation}"
        Logger.info ""
        Logger.info PlayerSeasonStats.formattedHeaderString()
        Logger.info ""

        stats = dbService.getPlayerSeasonStatsForPlayerIdAndSeason(playerId)

        stats.each do |stat|
            Logger.info stat.formattedString(player.positionCode)
        end

        Logger.taskEnd()
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
