class PlayerStatSeason
  attr_accessor :season
  attr_accessor :games, :goals, :assists, :points

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
