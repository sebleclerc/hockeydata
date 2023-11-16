class TeamCommand < BaseCommand
  desc "roster", "Show a list of NHL teams."
  def teams(teamId=nil)
    initTask()

    if teamId.nil?
      showTeams()
    else
      showRosterForTeamId(teamId)
    end
  end

  default_task :teams

  no_tasks do
    def showTeams
      Logger.taskTitle "Roster - Show all teams"

      teams = @dbService.getAllTeams()

      teams.each do |team|
        Logger.info "#{team.id.to_s.ljust(5)}#{team.name.ljust(25)}"
      end

      Logger.taskEnd
    end

    def showRosterForTeamId(id)
      team = @dbService.getTeamForId(id)
      roster = @dbService.getTeamRoster(team)

      Logger.taskTitle "Roster - Team #{id} #{team.name}"

      roster.each do |playerId|
        player = @dbService.getPlayerForId(playerId)
        fullName = player.nil? ? "" : player.fullName
        Logger.info "#{playerId.to_s.ljust(10)}#{fullName.ljust(25)}"
      end

      Logger.taskEnd
    end
  end
end
