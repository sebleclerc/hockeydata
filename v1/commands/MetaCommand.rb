#
# MetaCommand
#

class MetaCommand < BaseCommand
  desc "cache_pool", "Cache all pool rosters, import them and dump to database file."
  def cache_pool()
    CacheCommand.new.invoke("roster", [], :force => true)
    ImportCommand.new.invoke("import")
    system("mysqldump hockeydata > assets/database.sql")
  end

  # ./hockey.rb cache pool
  # && ./hockey.rb import &&
  ## mysqldump hockeydata > assets/database.sql
end
