class PlayerSalarySeason
  attr_accessor :season, :avv

  def self.showAVVHeader
    return "AVV".rjust(Constants.avvPadding).colorize(:yellow)
  end

  def showAVV
    if avv < 1000000
      show = avv.to_s
      .insert(3, ' ')
    elsif avv < 10000000
      show = avv.to_s
      .insert(1, ' ')
      .insert(5, ' ')
    else
      show = avv.to_s
      .insert(2, ' ')
      .insert(6, ' ')
    end

    show += " $"

    return show.rjust(Constants.avvPadding)
  end

  ## Loading
  def self.fromRow(row)
    salary = PlayerSalarySeason.new

    salary.season = row["season"]
    salary.avv = row["avv"]

    return salary
  end
end
