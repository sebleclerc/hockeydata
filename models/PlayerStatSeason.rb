class PlayerStatSeason
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

  private

  def poolPointsForForward
    return 2 * goals + assists
  end

  def poolPointsForDefense
    return 3 * goals + 1.5 * assists
  end
end
