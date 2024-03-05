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

    players = @dbService.getPoolRoster(
      Constants.currentSeason,
      [
        PoolDraftStatut::SELECTED,
        PoolDraftStatut::EXCHANGED,
        PoolDraftStatut::REVOKED
      ]
    )

    players.each do |player|
      @cacheService.cachePlayerForId(player, @force)
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

    cacheTeamPlayers(teamId)

    Logger.taskEnd
  end

  desc "teams ids", "Cache every players for every teams"
  def teams(teams)
    Logger.taskTitle "Caching teams (#{teams}) players"

    initTask()

    teamIds = teams.split(",")

    teamIds.each do |teamId|
      cacheTeamPlayers(teamId)
    end

    Logger.taskEnd
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

    players = @dbService.getPoolRoster(
      Constants.currentSeason,
      [
        PoolDraftStatut::SELECTED,
        PoolDraftStatut::EXCHANGED,
        PoolDraftStatut::REVOKED
      ]
    )
    players.each do |playerId|
      @cacheService.cachePlayerForId(playerId, true)
    end

    Logger.taskEnd
  end

  desc "salary", "Caching players stats with salary."
  def salary()
    Logger.taskTitle "Caching players stats with salary"

    initTask()

    roster = @dbService.getAvailablePlayerStatsSalaryForSeason(Constants.currentSeason)

    roster.each do |poolPlayer|
      @cacheService.cachePlayerForId(poolPlayer.player.id, true)
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

    def cacheTeamPlayers(teamId)
      team = @dbService.getTeamForId(teamId)
      roster = @dbService.getRosterForTeam(team)

      roster.each do |playerId|
        @cacheService.cachePlayerForId(playerId, @force)
      end
    end
  end
end
