class Player
    attr_accessor :id, :firstName, :lastName, :primaryNumber
    attr_accessor :birthYear, :birthMonth, :birthDay, :birthCity, :birthProvince, :birthCountry
    attr_accessor :height, :weight, :active, :shoot, :rookie, :teamId, :positionCode

    def self.showFullNameHeader
        return "Name".rjust(Constants.fullNamePadding).colorize(:yellow)
    end

    def showFullName
        return fullName.rjust(Constants.fullNamePadding)
    end

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
