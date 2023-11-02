class PoolPlayerValues
  attr_accessor :player, :stat, :salary, :value

  def initialize(player, stat, salary)
    @player = player
    @stat = stat
    @salary = salary
    @value = calculatePoolValue()
  end

  def poolPoints
    return stat.poolPoints(player.positionCode)
  end

  private

  def calculatePoolValue()
    position = player.positionCode
    poolPoints = stat.poolPoints(position)

    if poolPoints == 0
      @value = 0
    else
      intValue = salary.avv.to_f / poolPoints
      @value = intValue.to_i
    end
  end
end
