#
# Salary
#

class Salary < Thor
  desc "missing SEASON PLAYERID", "Add missing salary for a season and playerId"
  def missing(season, type, entityId = nil)
    Logger.taskTitle "Task salary missing for #{type}"
    initTask()

    case type
      when "player"
        Logger.info "To Be Developed"
      when "team"
        askMissingTeamSalary(season, entityId)
      else
        Logger.info "Incorrect missing type"
    end

    Logger.taskEnd()
  end

  no_tasks do
    def initTask
      @dbService = DatabaseService.new
    end

    def askMissingTeamSalary(season, teamId)
      # Get team info
      team = @dbService.getTeamForId(teamId)
      Logger.info "Salary for team #{team.name}"

      # Get players
      roster = @dbService.getTeamRoster(team)
      teamSalaries = Hash.new

      roster.each do |playerId|
        salary = TaskPlayerSeasonSalary.new

        player = @dbService.getPlayerForId(playerId)
        salary.player = player
        seasonSalary = @dbService.getPlayerSeasonSalary(playerId, season)
        salary.salary = seasonSalary

        teamSalaries[playerId] = salary
      end

      Logger.info "#{"ID".ljust(8)}#{"Name".rjust(30)}   #{"Salary".rjust(8)}"
      teamSalaries.each do |playerId, salary|
        Logger.info "#{playerId.to_s.ljust(8)}#{salary.fullName.rjust(30)}   #{salary.avv.rjust(8)}"
      end

      # Ask salary and insert
      teamSalaries.each do |playerId, salary|
        if salary.salary.nil?
          askMissingPlayerSalary(salary.player, season)
        end
      end
    end

    def askMissingPlayerSalary(player, season)
      Logger.info "Quel est le salary de #{player.fullName} (#{player.positionCode})(#{player.id}) pour #{season}?"
      salary = STDIN.gets.chomp

      if !(salary.nil? || salary.empty?)
        @dbService.insertPlayerSalary(player.id, season, salary)
      end
    end
  end
end

class TaskPlayerSeasonSalary
  attr_accessor :player, :salary

  def fullName
    return player.fullName
  end

  def avv
    if salary.nil?
      return ""
    else
      return salary.avv.to_s
    end
  end
end
