#
# CacheCommand
#

class CacheCommand < BaseCommand
  class_option :force, :type => :boolean

  desc "all", "Cache everything."
  def all()
    Logger.taskTitle "Caching for all"

    initTask()

    @cacheService.cachePositions(@force)
    @cacheService.cacheTeams(@force)

    cacheTeamRosters()

    roster = @dbService.getPoolPlayersForSeason(Constants.currentSeason)

    roster.each do |player|
      @cacheService.cachePlayerForId(player.id, @force)
      @cacheService.cachePlayerArchiveStatsForId(player.id, @force)
    end

    Logger.taskEnd
  end

  desc "roster", "Cache every team's roster."
  def roster()
    Logger.taskTitle "Caching teams' rosters"

    initTask()

    cacheTeamRosters()

    Logger.taskEnd
  end

  desc "team teadId", "Cache every players for a team."
  def team(teamId)
    Logger.taskTitle "Caching team's (#{teamId}) players"

    initTask()

    team = @dbService.getTeamForId(teamId)
    roster = @dbService.getTeamRoster(team)

    roster.each do |playerId|
      @cacheService.cachePlayerForId(playerId, @force)
      # @cacheService.cachePlayerArchiveStatsForId(playerId, @force)
    end
  end

  desc "players", "Cache for all players"
  def players()
    Logger.taskTitle "Caching all players"

    initTask()

    players = @dbService.getAllPlayers()

    players.each do |playerId|
      @cacheService.cachePlayerForId(playerId, @force)
    end

    Logger.taskEnd
  end

  desc "pool", "Cache pool players stats."
  def pool()
    Logger.taskTitle "Cache pool players stats"

    initTask()

    @dbService.getPoolRosterForSeason(Constants.currentSeason).each do |playerId|
      @cacheService.cachePlayerForId(playerId, false)
      @cacheService.cachePlayerArchiveStatsForId(playerId, true)
    end

    Logger.taskEnd
  end

  desc "salary", "Caching players stats with salary."
  def salary()
    Logger.taskTitle "Caching players stats with salary"

    initTask()

    roster = @dbService.getAvailablePlayerStatsSalaryForSeason(Constants.currentSeason)

    roster.each do |poolPlayer|
      @cacheService.cachePlayerArchiveStatsForId(poolPlayer.player.id, true)
    end

    Logger.taskEnd
  end

  no_tasks do
    def initTask()
      super
      @force = options[:force]
    end

    def cacheTeamRosters()
      Logger.info "Caching all team rosters"

      teams = @dbService.getAllTeams()
      teams.each do |team|
        @cacheService.cacheTeamRoster(team, @force)
      end
    end
  end
end
