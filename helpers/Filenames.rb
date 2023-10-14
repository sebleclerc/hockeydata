class Filenames
  def self.positions()
    return "positions.json"
  end

  def self.teams()
    return "teams.json"
  end

  def self.teamRoster(team)
    return "teams-#{team.id}-roster.json"
  end

  def self.playerForId(id)
    return "#{id}-player.json"
  end

  def self.playerArchiveStatsForId(id)
    return "#{id}-player-stats-archive.json"
  end
end
