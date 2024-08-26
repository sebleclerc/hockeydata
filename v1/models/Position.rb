class Position
    attr_accessor :code, :abbrev, :fullName, :type

    def self.fromJson(item)
        newPos = Position.new

        newPos.code = item["code"]
        newPos.abbrev = item["abbrev"]
        newPos.fullName = item["fullName"]
        newPos.type = item["type"]

        return newPos
    end

    def to_s
        return "Code => #{@code}
        Abbreviation => #{@abbrev}
        Fullname => #{@fullName}
        Type => #{@type}"
    end
end