class PlayerSalarySeason
  attr_accessor :season, :avv

  def self.fromRow(row)
    salary = PlayerSalarySeason.new

    salary.season = row["season"]
    salary.avv = row["avv"]

    return salary
  end
end
