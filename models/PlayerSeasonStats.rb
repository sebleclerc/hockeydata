class PlayerSeasonStats
  attr_accessor :season
  attr_accessor :games, :goals, :assists, :points
  attr_accessor :leagueName, :teamName

  ## Loading

  def self.fromRow(row)
    stat = PlayerSeasonStats.new

    stat.season = row["season"]

    stat.games = row["games"]
    stat.goals = row["goals"]
    stat.assists = row["assists"]
    stat.points = row["points"]

    stat.leagueName = row["leagueName"]
    stat.teamName = row["teamName"]

    return stat
  end

  ## Showing

  def self.formattedHeaderString
    header = "Season".rjust(10).colorize(:yellow)
    header += "Games".intHeader()
    header += "Goals".intHeader()
    header += "Assists".intHeader()
    header += "Points".intHeader()
    header += "Team Name".showHeader()
    header += "League Name".showHeader()
    header += "     "
    header += "Pool".intHeader()
    header += "Projected".floatHeader()

    return header
  end

  def self.formattedHeaderRows
    return [
      LoggerColumn.custom("Season", Constants.seasonPadding),
      LoggerColumn.int("GP"),
      LoggerColumn.int("G"),
      LoggerColumn.int("A"),
      LoggerColumn.int("P"),
      LoggerColumn.custom("Team Name", Constants.fullNamePadding),
      LoggerColumn.custom("League Name", Constants.fullNamePadding),
      LoggerColumn.float("Pool"),
      LoggerColumn.float("Proj.")
    ]
  end

  def formattedString(position)
    formatted = season.to_s.rjust(10)
    formatted += games.show()
    formatted += goals.show()
    formatted += assists.show()
    formatted += points.show()
    formatted += teamName.show()
    formatted += leagueName.show()
    formatted += "     "
    formatted += poolPoints(position).show()
    formatted += projectedPoolPoints(position).show()

    return formatted
  end

  def formattedRows(position)
    return [
      LoggerColumn.custom(season.to_s, Constants.seasonPadding),
      LoggerColumn.int("GP", games),
      LoggerColumn.int("G", goals),
      LoggerColumn.int("A", assists),
      LoggerColumn.int("P", points),
      LoggerColumn.custom(teamName, Constants.fullNamePadding),
      LoggerColumn.custom(leagueName, Constants.fullNamePadding),
      LoggerColumn.float("Pool", poolPoints(position)),
      LoggerColumn.float("Proj.", projectedPoolPoints(position))
    ]
  end

  def poolPoints(position)
    case position
      when "C","L","R"
        return poolPointsForForward()
      when "D"
        return poolPointsForDefense()
      else
        return 0
    end
  end

  def projectedPoolPoints(position)
    case position
      when "C","L","R"
        return projectedPoolPointsForForward()
      when "D"
        return projectedPoolPointsForDefense()
      else
        return 0
    end
  end

  def projectedGoals
    return (goals.to_f/games * 82).round(2)
  end

  def projectedAssists
    return (assists.to_f/games * 82).round(2)
  end

  private

  def poolPointsForForward
    return 2 * goals + assists
  end

  def poolPointsForDefense
    return 3 * goals + 1.5 * assists
  end

  def projectedPoolPointsForForward
    return (projectedGoals * 2 + projectedAssists).round(2)
  end

  def projectedPoolPointsForDefense
    return (projectedGoals * 3 + 1.5 * projectedAssists).round(2)
  end
end
