#
# SalaryCommand
#

class SalaryCommand < BaseCommand
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
    def askMissingTeamSalary(season, teamId)
      # Get team info
      team = @dbService.getTeamForId(teamId)
      Logger.info "Salary for team #{team.name}"

      # Get players
      roster = @dbService.getTeamRoster(team)
      teamSalaries = Hash.new

      roster.each do |playerId|
        Logger.debug "PlayerID : #{playerId}"
        salary = TaskPlayerSeasonSalary.new

        player = @dbService.getPlayerForId(playerId)
        salary.player = player
        seasonSalary = @dbService.getPlayerSeasonSalary(playerId, season)
        salary.salary = seasonSalary

        teamSalaries[playerId] = salary
      end

      Logger.info "#{"ID".ljust(8)}#{"Name".rjust(30)}   #{"Salary".rjust(12)}"
      teamSalaries.each do |playerId, salary|
        Logger.info "#{playerId.to_s.ljust(8)}#{salary.fullName.rjust(30)}   #{salary.avv()}"
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

      Logger.info "Pour combien d'année encore?"
      nbYears = STDIN.gets.chomp.to_i

      if !nbYears.nil? && nbYears > 0
        currentSeason = season

        nbYears.times do |i|
          nextSeason = calculateNextSeason(currentSeason)
          Logger.debug "Next season #{nextSeason}"
          @dbService.insertPlayerSalary(player.id, nextSeason, salary)

          currentSeason = nextSeason
        end
      end
    end

    def calculateNextSeason(season)
      firstHalf = season[4,4].to_i
      secondHalf = firstHalf + 1
      nextSeason = "#{firstHalf}#{secondHalf}"

      return nextSeason
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
      return salary.avv.showSalary()
    end
  end
end
