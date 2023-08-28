require "mysql2"
require_relative "../models/Player.rb"
require_relative "../models/PlayerStatSeason.rb"
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

            Logger.debug "Inserting into DB"
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

    def getAllTeams()
        teams = Array.new
        results = @dbClient.query("SELECT * FROM Teams")

        results.each do |row|
            team = Team.fromRow(row)
            teams.push(team)
        end

        return teams
    end

    def clearTeamPlayers
        result = @dbClient.query("TRUNCATE TABLE TeamsPlayers")
    end

    def insertTeamRoster(team, roster)
        roster.each do |player|
            result = @insertTeamPlayers.execute(
                team.id,
                player["person"]["id"]
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

    def getPlayerForId(playerId)
        players = Array.new
        results = @dbClient.query("SELECT * FROM Players WHERE id = #{playerId}")

        results.each do |row|
            player = Player.fromRow(row)
            players.append(player)
        end

        return players.first
    end

    def insertPlayerArchiveStat(playerId, playerStat)
        playerStat.each do |stat|
            @insertPlayerStats.execute(
                playerId,
                stat["season"],
                stat["stat"]["games"],
                stat["stat"]["goals"],
                stat["stat"]["assists"],
                stat["stat"]["points"],
                stat["stat"]["shots"],
                stat["stat"]["hits"],
                convertStringToTime(stat["stat"]["timeOnIce"]),
                stat["stat"]["shifts"],
                stat["stat"]["plusMinus"],
                stat["stat"]["shotPct"],
                stat["stat"]["penaltyMinutes"],
                stat["stat"]["powerPlayGoals"],
                stat["stat"]["powerPlayPoints"],
                convertStringToTime(stat["stat"]["powerPlayTimeOnIce"]),
                stat["stat"]["shortHandedGoals"],
                stat["stat"]["shortHandedPoints"],
                convertStringToTime(stat["stat"]["shortHandedTimeOnIce"]),
                stat["stat"]["gameWinningGoals"],
                stat["stat"]["overTimeGoals"],
                stat["league"]["id"],
                stat["league"]["name"],
                stat["team"]["id"],
                stat["team"]["name"],
            )
        end
    end

    def getPoolPlayersForSeason(season)
        players = Array.new
        results = @dbClient.query("SELECT p.id, p.firstName, p.lastName, positionCode FROM Players p, PoolDraft pd WHERE p.id = pd.playerId AND season = \"#{season}\"")

        results.each do |row|
            player = Player.fromPoolRow(row)
            players.append(player)
        end

        return players
    end

    def getPoolPlayerStatsForSeason(playerId, season)
        results = @dbClient.query("SELECT * FROM PlayersStatsArchive WHERE playerId = #{playerId} AND season = \"#{season}\" AND teamId IS NOT NULL")
        stats = Array.new

        results.each do |result|
            stat = PlayerStatSeason.new

            stat.season = result["season"]
            stat.games = result["games"]
            stat.goals = result["goals"]
            stat.assists = result["assists"]
            stat.points = result["points"]

            stats.append(stat)
        end

        return stats.first
    end

    private

    def prepareStatements
        @insertTeam = @dbClient.prepare("REPLACE INTO Teams (id,name,venue,abbreviation,firstYearOfPlay,divisionId,conferenceId,franchiseid,active) VALUES (?,?,?,?,?,?,?,?,?)")
        @insertTeamPlayers = @dbClient.prepare("REPLACE INTO TeamsPlayers (teamId,playerId) VALUES (?,?)")
        @insertPosition = @dbClient.prepare("REPLACE INTO Positions (code,abbrev,fullName,type) VALUES (?,?,?,?)")
        @insertPlayer = @dbClient.prepare("REPLACE INTO Players (id,firstName,lastName,primaryNumber,birthYear,birthMonth,birthDay,birthCity,birthProvince,birthCountry,height,weight,active,shoot,rookie,teamId,positionCode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
        @insertPlayerStats = @dbClient.prepare("REPLACE INTO PlayersStatsArchive (playerId,season,games,goals,assists,points,shots,hits,timeOnIce,shifts,plusMinus,shotPct,penaltyMinutes,powerPlayGoals,powerPlayPoints,powerPlayTimeOnIce,shortHandedGoals,shortHandedPoints,shortHandedTimeOnIce,gameWinningGoals,overTimeGoals,leagueId,leagueName,teamId,teamName) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
    end

    def convertStringToTime(value)
        if value != nil
            parts = value.split(":")
            firstPart = parts[0].to_f

            secondPart = parts[1].to_f
            secondsPercent = secondPart / 60

            return firstPart + secondsPercent
        else
            return value
        end
    end
end
