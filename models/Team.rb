class Team
    attr_accessor :id, :name, :venue, :abbreviation, :firstYearOfPlay, :divisionId, :conferenceId, :franchiseId, :active

    def self.fromJson(item)
        newTeam = Team.new

        newTeam.id = item["id"]
        newTeam.name = item["name"]
        newTeam.venue = item["venue"]["name"]
        newTeam.abbreviation = item["abbreviation"]
        newTeam.firstYearOfPlay = item["firstYearOfPlay"]
        newTeam.divisionId = item["division"]["id"]
        newTeam.conferenceId = item["conference"]["id"]
        newTeam.franchiseId = item["franchise"]["franchiseId"]
        newTeam.active = item["active"]

        return newTeam
    end
end