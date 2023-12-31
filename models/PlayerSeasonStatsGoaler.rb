class PlayerSeasonStatsGoaler
  attr_accessor :season
  attr_accessor :games, :gamesStarted
  attr_accessor :wins, :losses, :ot, :shutouts
  attr_accessor :leagueName, :teamName
  attr_accessor :victoiresProlongation, :victoiresFusillade

  def self.formattedHeaderString
    header = "Season".rjust(10).colorize(:yellow)
    header += "Games".intHeader()
    header += "Started".intHeader()
    header += "Wins".intHeader()
    header += "Losses".intHeader()
    header += "OT".intHeader()
    header += "SO".intHeader()
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
      LoggerColumn.int("GS"),
      LoggerColumn.int("W"),
      LoggerColumn.int("L"),
      LoggerColumn.int("OT"),
      LoggerColumn.int("SO"),
      LoggerColumn.custom("Team Name", Constants.fullNamePadding),
      LoggerColumn.custom("League", Constants.leagueNamePadding),
      LoggerColumn.float("Pool"),
      LoggerColumn.float("Proj.")
    ]
  end

  def formattedString()
    formatted = season.to_s.rjust(10)
    formatted += games.show()
    formatted += gamesStarted.show()
    formatted += wins.show()
    formatted += losses.show()
    formatted += ot.show()
    formatted += shutouts.show()
    formatted += teamName.show()
    formatted += leagueName.show()
    formatted += "     "
    formatted += poolPoints().show()
    formatted += projectedPoolPoints().show()

    return formatted
  end

  def formattedRows()
    return [
      LoggerColumn.custom(season.to_s, Constants.seasonPadding),
      LoggerColumn.int("GP", games),
      LoggerColumn.int("GS", gamesStarted),
      LoggerColumn.int("W", wins),
      LoggerColumn.int("L", losses),
      LoggerColumn.int("OT", ot),
      LoggerColumn.int("SO", shutouts),
      LoggerColumn.custom(teamName, Constants.fullNamePadding),
      LoggerColumn.custom(leagueName, Constants.leagueNamePadding),
      LoggerColumn.float("Pool", poolPoints),
      LoggerColumn.float("Proj.", projectedPoolPoints)
    ]
  end

  def poolPoints()
    points = 0

    # Should be getting back this soon soon soon
    # realWins = wins - victoiresProlongation - victoiresFusillade
    # points += realWins*3
    # points += victoiresFusillade*2
    points += wins*3
    points += shutouts*3

    return points
  end

  def projectedPoolPoints()
    points = 0

    points += projectedWins()*3
    points += projectedShutouts()*3

    return points.round(2)
  end

  def projectedWins
    return (wins.to_f/games * 65).round(2)
  end

  def projectedShutouts
    return (shutouts.to_f/games * 65).round(2)
  end
end
