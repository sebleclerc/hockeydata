#
# SalaryCommand
#

class SalaryMissingCommand < BaseCommand
  desc "all season", "Add missing all missing salary for a season"
  def all(season=Constants.currentSeason)
    Logger.taskTitle "All missing salary for #{season}"

    initTask()

    # Get all combined rosters
    roster = @dbService.getAllRosters()

    roster.each do |playerId|
      player = @dbService.getPlayerForId(playerId)
      salary = @dbService.getPlayerSeasonSalary(playerId, season)

      if !player.nil? && salary.nil?
        system("open", "https://www.capfriendly.com/search?s=#{player.lastName}")
        askMissingPlayerSalary(player, season)
      end
    end

    Logger.taskEnd
  end

  desc "team teamId season", "Add missing salary info for a specific team for a season."
  def team(teamId, season=Constants.currentSeason)
    Logger.taskTitle "Missing Team #{teamId} salary for #{season}"

    initTask()

    # Get team info
    team = @dbService.getTeamForId(teamId)
    Logger.info "Salary for team #{team.name}"

    # Get players
    roster = @dbService.getRosterForTeam(team)
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

    Logger.taskEnd
  end

  desc "player playerId season", "Add missing salary info for a specific player."
  def player(playerId, season=Constants.currentSeason)
    Logger.taskTitle "Missing Player #{playerId} salary for #{season}"

    initTask()

    askMissingPlayerSalary(playerId, season)

    Logger.taskEnd
  end

  no_tasks do
    def askMissingPlayerSalary(player, season)
      Logger.info "Quel est le salary de #{player.fullName} (#{player.positionCode})(#{player.id}) pour #{season}?"
      salary = STDIN.gets.chomp

      if !(salary.nil? || salary.empty?)
        sanitizedSalary = salary.gsub! ",", ""
        @dbService.insertPlayerSalary(player.id, season, sanitizedSalary)
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

class SalaryCommand < BaseCommand
  desc "missing", "Add missing salary informations"
  subcommand "missing", SalaryMissingCommand
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
      return salary.showAVV()
    end
  end
end
