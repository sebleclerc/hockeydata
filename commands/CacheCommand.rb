#
# CacheCommand
#

class CacheCommand
  def initialize(cacheService, dbService)
    @cacheService = cacheService
    @dbService = dbService
  end

  def run(type, force)
    Logger.taskTitle "Cache command for type #{type}"

    case type
      when "all"
        cacheAll(force)
      when "roster"
        cacheTeamRosters(force)
      when "team"
        cacheTeamPlayers(force)
      when "salary"
        cacheSalaryPlayers()
      when "pool"
        cachePool()
      else
        Logger.error "Wrong cache type: [#{type}]"
    end

    Logger.taskEnd()
  end

  private

  def cacheAll(force)
    Logger.info "Caching for all"
    @cacheService.cachePositions(force)
    @cacheService.cacheTeams(force)

    cacheTeamRosters(force)

    roster = @dbService.getPoolPlayersForSeason(Constants.currentSeason)

    roster.each do |player|
      @cacheService.cachePlayerForId(player.id, force)
      @cacheService.cachePlayerArchiveStatsForId(player.id, force)
    end
  end

  def cacheTeamRosters(force)
    Logger.info "Caching team's rosters"

    teams = @dbService.getAllTeams()
    teams.each do |team|
      @cacheService.cacheTeamRoster(team, force)
    end
  end

  def cacheTeamPlayers(force)
    Logger.info "Caching team's players"

    Logger.info "What team ID?"
    teamId = STDIN.gets.chomp
    team = @dbService.getTeamForId(teamId)
    roster = @dbService.getTeamRoster(team)

    roster.each do |playerId|
      @cacheService.cachePlayerForId(playerId, force)
      @cacheService.cachePlayerArchiveStatsForId(playerId, force)
    end
  end

  def cacheSalaryPlayers()
    Logger.info "Caching players stats with salary"
    roster = @dbService.getAvailablePlayerStatsSalaryForSeason(Constants.currentSeason)

    roster.each do |poolPlayer|
      @cacheService.cachePlayerArchiveStatsForId(poolPlayer.player.id, true)
    end
  end

  def cachePool()
    Logger.info "Refresh caching for pool"
    @dbService.getPoolRosterForSeason(Constants.currentSeason).each do |playerId|
      @cacheService.cachePlayerForId(playerId, false)
      @cacheService.cachePlayerArchiveStatsForId(playerId, true)
    end
  end
end
