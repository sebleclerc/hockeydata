require "mysql2"
require_relative "../models/Player.rb"
require_relative "../models/PlayerSeasonStats.rb"
require_relative "../models/PlayerSeasonStatsGoaler.rb"
require_relative "../models/PlayerSalarySeason.rb"
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

    def getTeamForId(teamId)
        results = @dbClient.query("SELECT * FROM Teams where id = #{teamId}")
        row = results.each.first
        return Team.fromRow(row)
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

    def getAllRosters()
        roster = Array.new

        results = @dbClient.query("SELECT * FROM TeamsPlayers")

        results.each do |row|
            roster.append(row["playerId"])
        end

        return roster
    end

    def getTeamRoster(team)
        roster = Array.new
        results = @dbClient.query("SELECT * FROM TeamsPlayers WHERE teamId = #{team.id}")

        results.each do |row|
            roster.append(row["playerId"])
        end

        return roster
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

    def insertPlayerArchiveStatGoaler(playerId, playerStat)
        playerStat.each do |stat|
            @insertPLayerStatsGoaler.execute(
                playerId,
                stat["season"],
                stat["stat"]["games"],
                stat["stat"]["gamesStarted"],
                stat["stat"]["ot"],
                stat["stat"]["shutouts"],
                stat["stat"]["wins"],
                stat["stat"]["losses"],
                convertStringToTime(stat["stat"]["timeOnIce"]),
                stat["stat"]["savePercentage"],
                stat["league"]["id"],
                stat["league"]["name"],
                stat["team"]["id"],
                stat["team"]["name"]
            )
        end
    end

    def getPlayerSeasonStatsForPlayerIdAndSeason(playerId, season=nil)
        stats = Array.new

        query = "SELECT * FROM PlayersStatsArchive WHERE playerId = #{playerId}"

        if !season.nil?
            query += " AND season = \"#{season}\""
        end

        query += " AND teamId IS NOT NULL"

        results = @dbClient.query(query)

        results.each do |result|
            stat = PlayerSeasonStats.new

            stat.season = result["season"]

            stat.games = result["games"]
            stat.goals = result["goals"]
            stat.assists = result["assists"]
            stat.points = result["points"]

            stat.leagueName = result["leagueName"]
            stat.teamName = result["teamName"]

            stats.append(stat)
        end

        return stats
    end

    def getPlayerSeasonStatsGoalerForPlayerIdAndSeason(playerId, season=nil)
        stats = Array.new

        query = "SELECT * FROM PlayersStatsArchiveGoaler WHERE playerId = #{playerId}"

        if !season.nil?
            query += " AND season = \"#{season}\""
        end

        query += " AND teamId IS NOT NULL"

        results = @dbClient.query(query)

        results.each do |result|
            stat = PlayerSeasonStatsGoaler.new

            stat.season = result["season"]

            stat.games = result["games"]
            stat.gamesStarted = result["gamesStarted"]

            stat.ot = result["ot"]
            stat.shutouts = result["shutouts"]
            stat.wins = result["wins"]
            stat.losses = result["losses"]

            stat.leagueName = result["leagueName"]
            stat.teamName = result["teamName"]

            stats.append(stat)
        end

        return stats
    end

    def getPoolRosterForSeason(season,all=false)
        roster = Array.new
        query = "SELECT * FROM PoolDraft WHERE season = \"#{season}\""

        if !all
            query += " AND active = 1"
        end

        results = @dbClient.query(query)

        results.each do |row|
            roster.append(row["playerId"])
        end

        return roster
    end

    def getPoolRosterPositionForSeason(season, position, all=false)
        roster = Array.new
        query = "SELECT pd.playerId FROM PoolDraft pd, Players p WHERE pd.playerId = p.id AND season = \"#{season}\""

        if position == 'G'
            query += " AND p.positionCode = \"G\""
        else
            query += " AND p.positionCode != \"G\""
        end

        if !all
            query += " AND pd.active = 1"
        end

        results = @dbClient.query(query)

        results.each do |row|
            roster.append(row["playerId"])
        end

        return roster
    end

    def insertPlayerSalary(playerId, season, salary)
        result = @insertPlayerSalary.execute(
            playerId,
            season,
            salary
        )
    end

    def getPlayerSeasonSalary(playerId, season)
        result = @dbClient.query("SELECT * FROM PlayersSalaries WHERE playerId = #{playerId} AND season = #{season}")
        row = result.each.first

        if row != nil
            return PlayerSalarySeason.fromRow(row)
        end

        return nil
    end

    private

    def prepareStatements
        @insertTeam = @dbClient.prepare("REPLACE INTO Teams (id,name,venue,abbreviation,firstYearOfPlay,divisionId,conferenceId,franchiseid,active) VALUES (?,?,?,?,?,?,?,?,?)")
        @insertTeamPlayers = @dbClient.prepare("REPLACE INTO TeamsPlayers (teamId,playerId) VALUES (?,?)")
        @insertPosition = @dbClient.prepare("REPLACE INTO Positions (code,abbrev,fullName,type) VALUES (?,?,?,?)")
        @insertPlayer = @dbClient.prepare("REPLACE INTO Players (id,firstName,lastName,primaryNumber,birthYear,birthMonth,birthDay,birthCity,birthProvince,birthCountry,height,weight,active,shoot,rookie,teamId,positionCode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
        @insertPlayerStats = @dbClient.prepare("REPLACE INTO PlayersStatsArchive (playerId,season,games,goals,assists,points,shots,hits,timeOnIce,shifts,plusMinus,shotPct,penaltyMinutes,powerPlayGoals,powerPlayPoints,powerPlayTimeOnIce,shortHandedGoals,shortHandedPoints,shortHandedTimeOnIce,gameWinningGoals,overTimeGoals,leagueId,leagueName,teamId,teamName) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
        @insertPLayerStatsGoaler = @dbClient.prepare("REPLACE INTO PlayersStatsArchiveGoaler (playerId,season,games,gamesStarted,ot,shutouts,wins,losses,timeOnIce,savePercentage,leagueId,leagueName,teamId,teamName) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
        @insertPlayerSalary = @dbClient.prepare("INSERT INTO PlayersSalaries (playerId,season,avv) VALUES (?,?,?)")
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
