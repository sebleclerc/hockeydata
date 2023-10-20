require_relative('../helpers/Float+Extensions.rb')
require_relative('../helpers/Integer+Extensions.rb')
require_relative('../helpers/String+Extensions.rb')

class PlayerSeasonStats
  attr_accessor :season
  attr_accessor :games, :goals, :assists, :points
  attr_accessor :leagueName, :teamName

  def self.formattedHeaderString
    header = "Season".ljust(10)
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

  def formattedString(position)
    formatted = season.to_s.ljust(10)
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
