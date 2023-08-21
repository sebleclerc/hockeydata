#!/Users/sleclerc/.rvm/rubies/ruby-2.7.2/bin/ruby

require "thor"
require "http"
require "mysql2"
require "./models/Position"

class Hockey < Thor
    desc "hockey build", "Build local data"
    def build()
        initTask()

        p "Building data..."

        results = @dbClient.query("SELECT * from Positions")

        response = HTTP.get("https://statsapi.web.nhl.com/api/v1/positions")
        parsedResp = response.parse

        parsedResp.each do |item|
            position = Position.fromJson(item)
            puts "Got position"
            puts position

            puts "Inserting into DB"
            result = @insertPosition.execute(position.code, position.abbrev, position.fullName, position.type)
        end
    end

    no_tasks do
        def initTask()
            @dbClient = Mysql2::Client.new(:host => "localhost", :database => "hockeydata", :username => "sleclerc", :password => "sleclerc")
            @insertPosition = @dbClient.prepare("REPLACE INTO Positions (code,abbrev,fullName,type) VALUES (?,?,?,?)")
        end
    end
end

Hockey.start(ARGV)