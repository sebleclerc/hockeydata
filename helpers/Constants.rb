class Constants
  # Easy access from everywhere
  @@currentSeason = "20232024"

  def self.currentSeason
    return @@currentSeason
  end

  # All show padding
  def self.fullNamePadding
    return 30
  end

  def self.avvPadding
    return 15
  end
end
