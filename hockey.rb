#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby
#
# Hockey base app
#

require "thor"
Dir["./commands/*.rb"].sort.each {|file| require file }
Dir["./helpers/*.rb"].each {|file| require file }
Dir["./services/*.rb"].each {|file| require file }

class Hockey < Thor
    desc "cache", "Fetch and cache files"
    subcommand "cache", CacheCommand

    desc "import", "Import JSON files into databse."
    subcommand "import", ImportCommand

    desc "team [teamId]", "Team related commands."
    subcommand "team", TeamCommand

    desc "player playerId", "Show informations about a specific player."
    subcommand "player", PlayerCommand

    desc "pool", "All pool related tasks."
    subcommand "pool", PoolCommand

    desc "salary", "All salary related tasks."
    subcommand "salary", SalaryCommand
end

Hockey.start(ARGV)
