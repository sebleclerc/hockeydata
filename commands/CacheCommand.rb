class CacheCommand
  def initialize(cacheService)
    @cacheService = cacheService
  end

  def run(type)
    Logger.taskTitle "Cache command for type #{type}"

    case type
      when "all"
        Logger.info "Caching for all"
        cacheAll()
      else
        Logger.error "Wrong cache type: [#{type}]"
    end

    Logger.taskEnd()
  end

  private

  def cacheAll
    Logger.info "Cache everything"
    @cacheService.cachePositions()
    @cacheService.cacheTeams()
    # Missing teams
    # @cacheService.cacheTeamsRoster(teams)

    # playerIds.each do |playerId|
      # cachePlayerForId(playerId)
      # cachePlayerArchiveStatsForId(playerId)
    # end
  end
end
