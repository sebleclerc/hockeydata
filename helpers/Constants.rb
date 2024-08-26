class Constants
  # Easy access from everywhere
  @@currentSeason = "20242025"

  def self.currentSeason
    return @@currentSeason
  end

  # All show
  def self.idPadding
    return 8
  end

  def self.fullNamePadding
    return 30
  end

  def self.leagueNamePadding
    return 10
  end

  def self.avvPadding
    return 15
  end

  def self.intPadding
    return 5
  end

  def self.floatPadding
    return 10
  end

  def self.seasonPadding
    return 10
  end
end
