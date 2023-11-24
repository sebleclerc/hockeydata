class TeamsCommand < BaseCommand
  desc "list", "Show a list of NHL teams."
  def list(teamId=nil)
    initTask()

    if teamId.nil?
      showTeams()
    else
      showRosterForTeamId(teamId)
    end
  end

  default_task :list

  no_tasks do
    def showTeams
      Logger.taskTitle "Roster - Show all teams"

      teams = @dbService.getAllTeams()

      proportionPadding = 10
      Logger.header([
        LoggerColumn.id(),
        LoggerColumn.name(),
        LoggerColumn.custom("P %", proportionPadding)
      ])

      teams.each do |team|
        teamRoster = @dbService.getRosterForTeam(team)
        nbPlayersRoster = teamRoster.count()
        nbPlayerInDb = 0

        teamRoster.each do |playerId|
          player = @dbService.getPlayerForId(playerId)

          unless player.nil?
            nbPlayerInDb += 1
          end
        end

        proportion = "#{nbPlayerInDb} / #{nbPlayersRoster}"
        Logger.row([
          LoggerColumn.id(team.id),
          LoggerColumn.name(team.name),
          LoggerColumn.custom(proportion, proportionPadding)
        ])
      end

      Logger.taskEnd
    end

    def showRosterForTeamId(id)
      team = @dbService.getTeamForId(id)
      roster = @dbService.getRosterForTeam(team)

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
