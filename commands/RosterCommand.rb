class RosterCommand
  def initialize(dbService)
    @dbService = dbService
  end

  def run(teamId)
    Logger.taskTitle "Roster command"

    if teamId.nil?
      showTeams()
    else
      showRosterForTeamId(teamId)
    end

    Logger.taskEnd()
  end

  private

  def showTeams
    Logger.info "All teams"
    Logger.info ""

    teams = @dbService.getAllTeams()

    teams.each do |team|
      Logger.info "#{team.id.to_s.ljust(5)}#{team.name.ljust(25)}"
    end

    Logger.info ""
  end

  def showRosterForTeamId(id)
    team = @dbService.getTeamForId(id)
    roster = @dbService.getTeamRoster(team)

    Logger.info "Roster for team #{id} #{team.name}"
    Logger.info ""

    roster.each do |playerId|
      player = @dbService.getPlayerForId(playerId)
      fullName = player.nil? ? "" : player.fullName
      Logger.info "#{playerId.to_s.ljust(10)}#{fullName.ljust(25)}"
    end

    Logger.info ""
  end
end
