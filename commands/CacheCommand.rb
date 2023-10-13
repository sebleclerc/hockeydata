require_relative("../helpers/PoolRoster.rb")

class CacheCommand
  def initialize(cacheService, dbService)
    @cacheService = cacheService
    @dbService = dbService
  end

  def run(type, force)
    Logger.taskTitle "Cache command for type #{type}"

    case type
      when "all"
        Logger.info "Caching for all"
        cacheAll(force)
      else
        Logger.error "Wrong cache type: [#{type}]"
    end

    Logger.taskEnd()
  end

  private

  def cacheAll(force)
    Logger.info "Cache everything"
    @cacheService.cachePositions(force)
    @cacheService.cacheTeams(force)

    teams = @dbService.getAllTeams()
    @cacheService.cacheTeamsRoster(teams, force)

    PoolRoster.rosterPlayerIds.each do |playerId|
      @cacheService.cachePlayerForId(playerId, force)
      @cacheService.cachePlayerArchiveStatsForId(playerId, force)
    end
  end
end
