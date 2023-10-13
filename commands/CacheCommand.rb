class CacheCommand
  def initialize(cacheService)
    @cacheService = cacheService
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
    # Missing teams
    # @cacheService.cacheTeamsRoster(teams)

    # playerIds.each do |playerId|
      # cachePlayerForId(playerId)
      # cachePlayerArchiveStatsForId(playerId)
    # end
  end
end
