require "thor"

class BaseCommand  < Thor
  no_tasks do
    def initTask()
        @dbService = DatabaseService.new
        @cacheService = CacheService.new
        @importService = ImportService.new(@dbService)
    end
  end
end
