class Player
    attr_accessor :id, :firstName, :lastName, :primaryNumber
    attr_accessor :birthYear, :birthMonth, :birthDay, :birthCity, :birthProvince, :birthCountry
    attr_accessor :height, :weight, :active, :shoot, :rookie, :teamId, :positionCode

    def fullName
        return "#{firstName} #{lastName}"
    end

    def birthDate
        return "#{birthYear}-#{birthMonth}-#{birthDay}"
    end

    def birthLocation
        location = birthCity

        if birthProvince |= nil
            location += ", #{birthProvince}"
        end

        location += ", #{birthCountry}"
    end

    def self.fromJson(item)
        # p item
        newPlayer = Player.new

        newPlayer.id = item["id"]
        newPlayer.firstName = item["firstName"]
        newPlayer.lastName = item["lastName"]
        newPlayer.primaryNumber = item["primaryNumber"]

        birthDate = item["birthDate"]
        birthDateParts = birthDate.split("-")
        newPlayer.birthYear = birthDateParts[0]
        newPlayer.birthMonth = birthDateParts[1]
        newPlayer.birthDay = birthDateParts[2]
        newPlayer.birthCity = item["birthCity"]
        newPlayer.birthProvince = item["birthStateProvince"]
        newPlayer.birthCountry = item["birthCountry"]

        newPlayer.height = item["height"]
        newPlayer.weight = item["weight"]
        newPlayer.active = item["active"]
        newPlayer.shoot = item["shootsCatches"]
        newPlayer.rookie = item["rookie"]
        newPlayer.teamId = item["currentTeam"]["id"]
        newPlayer.positionCode = item["primaryPosition"]["code"]

        return newPlayer
    end

    def self.fromPoolRow(row)
        player = Player.new

        player.id = row["id"]
        player.firstName = row["firstName"]
        player.lastName = row["lastName"]
        player.positionCode = row["positionCode"]

        return player
    end

    def self.fromRow(row)
        player = Player.new

        player.id = row["id"]

        player.firstName = row["firstName"]
        player.lastName = row["lastName"]
        player.primaryNumber = row["primaryNumber"]

        player.birthYear = row["birthYear"]
        player.birthMonth = row["birthMonth"]
        player.birthDay = row["birthDay"]

        player.birthCity = row["birthCity"]
        player.birthProvince = row["birthStateProvince"]
        player.birthCountry = row["birthCountry"]

        player.positionCode = row["positionCode"]

        return player
    end
end
