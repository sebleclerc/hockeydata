require "mysql2"
require_relative "../models/Player.rb"
require_relative "../models/PlayerSeasonStats.rb"
require_relative "../models/PlayerSeasonStatsGoaler.rb"
require_relative "../models/PlayerSalarySeason.rb"
require_relative "../models/PoolPlayerValues.rb"
require_relative "../models/Position.rb"
require_relative "../models/Team.rb"

module DatabaseServicePositions
    def insertPositions(positions)
        positions.each do |item|
            position = Position.fromJson(item)

            result = @insertPosition.execute(
                position.code,
                position.abbrev,
                position.fullName,
                position.type
            )
        end
    end
end

module DatabaseServiceTeams
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
end

module DatabaseServiceRoster
    def clearTeamRosters()
        result = @dbClient.query("TRUNCATE TABLE TeamsPlayers")
    end

    def insertTeamRoster(team, roster)
        roster.each do |player|
            result = @insertTeamPlayers.execute(
                team.id,
                player["id"]
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

    def getRosterForTeam(team)
        roster = Array.new
        results = @dbClient.query("SELECT * FROM TeamsPlayers WHERE teamId = #{team.id}")

        results.each do |row|
            roster.append(row["playerId"])
        end

        return roster
    end
end

class DatabaseService
    def initialize
        @dbClient = Mysql2::Client.new(:host => "localhost", :database => "hockeydata", :username => "sleclerc", :password => "sleclerc")
        prepareStatements()
    end

    include DatabaseServicePositions
    include DatabaseServiceTeams
    include DatabaseServiceRoster

    def getAllPlayers()
        players = Array.new

        results = @dbClient.query("SELECT * FROM Players")

        results.each do |row|
            players.append(row["id"])
        end

        return players
    end

    def insertPlayer(item)
        # p item

        birthDate = item["birthDate"]
        birthDateParts = birthDate.split("-")

        birthProvince = item["birthStateProvince"]["default"] unless item["birthStateProvince"].nil?

        result = @insertPlayer.execute(
            item["playerId"],
            item["firstName"]["default"],
            item["lastName"]["default"],
            item["sweaterNumber"],
            birthDateParts[0],
            birthDateParts[1],
            birthDateParts[2],
            item["birthCity"]["default"],
            birthProvince,
            item["birthCountry"],
            item["heightInInches"],
            item["weightInPounds"],
            item["isActive"],
            item["shootsCatches"],
            false, #Field not present anymore item["rookie"],
            item["currentTeamId"],
            item["position"],
            item["headshot"],
            item["heroImage"]
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

    def insertPlayerArchiveStat(playerId, playerStats)
        playerStats.each do |stat|
            @insertPlayerStats.execute(
                playerId,
                stat["season"],
                stat["gamesPlayed"],
                stat["goals"],
                stat["assists"],
                stat["points"],
                stat["shots"],
                nil, #stat["stat"]["hits"], does not exist anymore
                stat["avgToi"].to_f,
                nil, #|||stat["stat"]["shifts"],
                stat["plusMinus"],
                stat["shootingPctg"],
                stat["pim"],
                stat["powerPlayGoals"],
                stat["powerPlayPoints"],
                nil, #!!!convertStringToTime(stat["stat"]["powerPlayTimeOnIce"]),
                stat["shorthandedGoals"],
                stat["shorthandedPoints"],
                nil, #!!!convertStringToTime(stat["stat"]["shortHandedTimeOnIce"]),
                stat["gameWinningGoals"],
                stat["otGoals"],
                nil, #!!!stat["league"]["id"],
                stat["leagueAbbrev"],
                nil, #!!!stat["team"]["id"],
                stat["teamName"]["default"],
            )
        end
    end

    def insertPlayerArchiveStatGoaler(playerId, playerStats)
        playerStats.each do |stat|
            @insertPLayerStatsGoaler.execute(
                playerId,
                stat["season"],
                stat["gamesPlayed"],
                stat["gamesStarted"],
                stat["otLosses"],
                stat["shutouts"],
                stat["wins"],
                stat["losses"],
                convertStringToTime(stat["timeOnIce"]),
                stat["savePctg"],
                nil, #stat["league"]["id"],
                stat["leagueAbbrev"],
                nil, #stat["team"]["id"],
                stat["teamName"]["default"]
            )
        end
    end

    def getPlayerSeasonStatsForPlayerIdAndSeason(playerId, season=nil, league="NHL")
        stats = Array.new

        query = "SELECT * FROM PlayersStatsArchive WHERE playerId = #{playerId}"

        if !season.nil?
            query += " AND season = \"#{season}\""
        end

        query += " AND leagueName = \"#{league}\""
        query += " ORDER BY season"

        results = @dbClient.query(query)

        results.each do |row|
            stat = PlayerSeasonStats.fromRow(row)
            stats.append(stat)
        end

        return stats
    end

    def getPlayerSeasonStatsGoalerForPlayerIdAndSeason(playerId, season=nil, league="NHL")
        stats = Array.new

        query = "SELECT * FROM PlayersStatsArchiveGoaler WHERE playerId = #{playerId}"

        if !season.nil?
            query += " AND season = \"#{season}\""
        end

        query += " AND leagueName = \"#{league}\""
        query += " ORDER BY season"

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

            stat.victoiresProlongation = result["victoiresProlongation"]
            stat.victoiresFusillade = result["victoiresFusillade"]

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

    def getPoolRoster(season, position, statut, all=false)
        roster = Array.new
        query = "SELECT pd.playerId FROM PoolDraft pd, Players p WHERE pd.playerId = p.id AND season = \"#{season}\""

        if position == 'G'
            query += " AND p.positionCode = \"G\""
        else
            query += " AND p.positionCode != \"G\""
        end

        if !all
            if statut.instance_of? Integer
                query += " AND pd.statut = #{statut}"
            else
                query += " AND pd.statut IN (#{statut.join(",")})"
            end
        end

        results = @dbClient.query(query)

        results.each do |row|
            roster.append(row["playerId"])
        end

        return roster
    end

    def addPlayerStatutTaken(playerId, season)
        result = @insertPoolDraft.execute(
            playerId,
            season,
            2,
            nil,
            nil
        )
    end

    def deletePlayerStatutTaken(playerId, season)
        result = @insertPoolDraft.execute(
            playerId,
            season,
            0,
            nil,
            nil
        )
    end

    def selectPlayerForPool(playerId, season, date, points)
        result = @insertPoolDraft.execute(
            playerId,
            season,
            3,
            date,
            points
        )
    end

    def revokePlayerForPool(playerId, season, date, points)
        result = @insertPoolDraft.execute(
            playerId,
            season,
            4,
            date,
            points
        )
    end

    def getAvailablePlayerStatsSalaryForSeason(season)
        results = @dbClient.query("SELECT * from Players p INNER JOIN PlayersStatsArchive psa ON p.id = psa.playerId INNER JOIN PlayersSalaries sal ON p.id = sal.playerId WHERE p.positionCode != 'G' AND psa.season = \"#{season}\" AND sal.season = \"#{season}\" AND psa.leagueName = \"NHL\" AND p.id NOT IN (SELECT playerId FROM PoolDraft WHERE statut IN (1,2)) ORDER BY lastName, firstName")
        players = Array.new

        results.each do |row|
            player = Player.fromRow(row)
            stat = PlayerSeasonStats.fromRow(row)
            salary = PlayerSalarySeason.fromRow(row)

            poolPlayer = PoolPlayerValues.new(player, stat, salary)
            players.append(poolPlayer)
        end

        # Returning a large number will make them at the end of the list
        return players.sort_by { |player| player.value == 0 ? 999999999999 : player.value }
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
        @insertPlayer = @dbClient.prepare("REPLACE INTO Players (id,firstName,lastName,primaryNumber,birthYear,birthMonth,birthDay,birthCity,birthProvince,birthCountry,height,weight,active,shoot,rookie,teamId,positionCode,headshotUrl,heroImageUrl) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
        @insertPlayerStats = @dbClient.prepare("REPLACE INTO PlayersStatsArchive (playerId,season,games,goals,assists,points,shots,hits,timeOnIce,shifts,plusMinus,shotPct,penaltyMinutes,powerPlayGoals,powerPlayPoints,powerPlayTimeOnIce,shortHandedGoals,shortHandedPoints,shortHandedTimeOnIce,gameWinningGoals,overTimeGoals,leagueId,leagueName,teamId,teamName) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
        @insertPLayerStatsGoaler = @dbClient.prepare("REPLACE INTO PlayersStatsArchiveGoaler (playerId,season,games,gamesStarted,ot,shutouts,wins,losses,timeOnIce,savePercentage,leagueId,leagueName,teamId,teamName) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
        @insertPlayerSalary = @dbClient.prepare("INSERT INTO PlayersSalaries (playerId,season,avv) VALUES (?,?,?)")
        @insertPoolDraft = @dbClient.prepare("REPLACE INTO PoolDraft (playerId,season,statut,dateChanged,poolPointsChanged) VALUES (?,?,?,?,?)")
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
