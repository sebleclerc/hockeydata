require "mysql2"
require_relative "../models/Player.rb"
require_relative "../models/Position.rb"
require_relative "../models/Team.rb"

class DatabaseService
    def initialize
        @dbClient = Mysql2::Client.new(:host => "localhost", :database => "hockeydata", :username => "sleclerc", :password => "sleclerc")
        prepareStatements()
    end

    def insertPositions(positions)
        positions.each do |item|
            position = Position.fromJson(item)
            puts "Got position"
            puts position

            puts "Inserting into DB"
            result = @insertPosition.execute(
                position.code, 
                position.abbrev, 
                position.fullName, 
                position.type
            )
        end
    end

    def insertTeams(teams)
        teams.each do |item|
            team = Team.fromJson(item)
            
            result = @insertTeam.execute(
                team.id,
                team.name,
                team.venue,
                team.abbreviation,
                team.firstYearOfPlay,
                team.divisionId,
                team.conferenceId,
                team.franchiseId,
                team.active
            )
        end
    end

    def insertPlayer(jPlayer)
        player = Player.fromJson(jPlayer)

        result = @insertPlayer.execute(
            player.id,
            player.firstName,
            player.lastName,
            player.primaryNumber,
            player.birthYear,
            player.birthMonth,
            player.birthDay,
            player.birthCity,
            player.birthProvince,
            player.birthCountry,
            player.height,
            player.weight,
            player.active,
            player.shoot,
            player.rookie,
            player.teamId,
            player.positionCode
        )
    end

    private

    def prepareStatements
        @insertTeam = @dbClient.prepare("REPLACE INTO Teams (id,name,venue,abbreviation,firstYearOfPlay,divisionId,conferenceId,franchiseid,active) VALUES (?,?,?,?,?,?,?,?,?)")
        @insertPosition = @dbClient.prepare("REPLACE INTO Positions (code,abbrev,fullName,type) VALUES (?,?,?,?)")
        @insertPlayer = @dbClient.prepare("REPLACE INTO Players (id,firstName,lastName,primaryNumber,birthYear,birthMonth,birthDay,birthCity,birthProvince,birthCountry,height,weight,active,shoot,rookie,teamId,positionCode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
    end
end