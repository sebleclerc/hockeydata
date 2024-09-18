package ca.sebleclerc.hockeydata.services

import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.PoolHelper
import ca.sebleclerc.hockeydata.models.cache.CacheRosterPlayer
import ca.sebleclerc.hockeydata.models.Player
import ca.sebleclerc.hockeydata.models.PlayerSalarySeason
import ca.sebleclerc.hockeydata.models.PlayerSkaterSeason
import ca.sebleclerc.hockeydata.models.PoolDraftStatut
import ca.sebleclerc.hockeydata.models.Season
import ca.sebleclerc.hockeydata.models.Team
import ca.sebleclerc.hockeydata.models.cache.CacheGoalerSeason
import ca.sebleclerc.hockeydata.models.cache.CachePlayer
import ca.sebleclerc.hockeydata.models.cache.CacheSkaterSeason
import ca.sebleclerc.hockeydata.models.fromResult
import ca.sebleclerc.hockeydata.models.fromRow
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.sql.Types
import java.util.Properties

private const val url = "jdbc:mariadb://127.0.0.1:3306/hockeydata"
private const val username = "sleclerc"
private const val password = "sleclerc"

class DatabaseService {
  private val connection: Connection
  private val statement: Statement

  init {
    Class.forName("org.mariadb.jdbc.Driver")

    val props = Properties()
    props.setProperty("user", username)
    props.setProperty("password", password)

    connection = DriverManager.getConnection(url, props)
    statement = connection.createStatement()
  }

  // Teams

  fun getAllTeams(): List<Team> {
    val teams = mutableListOf<Team>()

    val rs = statement.executeQuery("SELECT * FROM Teams")
    while (rs.next()) { teams.add(Team.fromResult(rs)) }

    return teams
  }

  fun getTeamForId(teamId: Int): Team? {
    val rs = statement.executeQuery("SELECT * FROM Teams where id = $teamId")

    return if (rs.next()) Team.fromResult(rs) else null
  }

  fun clearRosters() {
    statement.execute("TRUNCATE TABLE TeamsPlayers")
  }

  fun insertTeamRoster(team: Team, players: List<CacheRosterPlayer>) {
    val prepareAddRoster = connection.prepareStatement("REPLACE INTO TeamsPlayers (teamId,playerId) VALUES (?,?)")

    players.forEach { player ->
      prepareAddRoster.setInt(1, team.id)
      prepareAddRoster.setInt(2, player.id)
      prepareAddRoster.execute()
    }
  }

  fun getRosterForTeam(teamId: Int): List<Int> {
    val players = mutableListOf<Int>()

    val rs = statement.executeQuery("SELECT * FROM TeamsPlayers WHERE teamId = $teamId")
    while (rs.next()) { players.add(rs.getInt("playerId")) }

    return players
  }

  // Players

  fun getAllPlayers(onlyGoalers: Boolean? = null): List<Player> {
    val players = mutableListOf<Player>()
    var query = "SELECT * FROM Players"

    onlyGoalers?.also {
      query += if (it) " WHERE positionCode = 'G'" else " WHERE positionCode != 'G'"
    }
    
    val rs = statement.executeQuery(query)

    while (rs.next()) {
      players.add(Player.fromRow(rs))
    }

    return players
  }

  fun getPlayerForId(playerId: Int): Player? {
    val rs = statement.executeQuery("SELECT * FROM Players WHERE id = $playerId")

    return if (rs.next()) Player.fromRow(rs) else null
  }

  fun insertPlayers(player: CachePlayer) {
    val birthSections = player.birthDate.split('-')
    val birthYear = birthSections[0].toInt()
    val birthMonth = birthSections[1].toInt()
    val birthDay = birthSections[2].toInt()

    val insertPlayer = connection.prepareStatement("REPLACE INTO Players (id,firstName,lastName,primaryNumber,birthYear,birthMonth,birthDay,birthCity,birthProvince,birthCountry,height,weight,active,shoot,rookie,teamId,positionCode,headshotUrl,heroImageUrl) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
    insertPlayer.setInt(1, player.playerId)
    insertPlayer.setString(2, player.firstName.default)
    insertPlayer.setString(3, player.lastName.default)
    insertPlayer.setInt(4, player.sweaterNumber ?: 0)
    insertPlayer.setInt(5, birthYear)
    insertPlayer.setInt(6, birthMonth)
    insertPlayer.setInt(7, birthDay)
    insertPlayer.setString(8, player.birthCity?.default)
    insertPlayer.setString(9, player.birthStateProvince?.default)
    insertPlayer.setString(10, player.birthCountry)
    insertPlayer.setInt(11, player.heightInInches)
    insertPlayer.setInt(12, player.weightInPounds)
    insertPlayer.setBoolean(13, player.isActive)
    insertPlayer.setString(14, player.shootsCatches)
    insertPlayer.setBoolean(15, false)
    insertPlayer.setObject(16, player.currentTeamId, Types.INTEGER)
    insertPlayer.setString(17, player.position)
    insertPlayer.setString(18, player.headshot)
    insertPlayer.setString(19, player.heroImage)
    insertPlayer.execute()
  }

  fun getSeasonsForSkaterId(playerId: Int): List<PlayerSkaterSeason> {
    val seasons = mutableListOf<PlayerSkaterSeason>()
    val rs = statement.executeQuery("SELECT * FROM PlayersStatsArchive WHERE leagueName = 'NHL' AND gameTypeId = 2 AND playerId = $playerId ORDER BY season DESC LIMIT 5")

    while (rs.next()) {
      seasons.add(PlayerSkaterSeason.fromRow(rs))
    }

    return seasons
  }

  fun insertSkaterSeason(player: CachePlayer, stat: CacheSkaterSeason) {
    val poolPoints = PoolHelper.getSkaterPoolPoint(player, stat)

    val insertStats = connection.prepareStatement("REPLACE INTO PlayersStatsArchive (playerId,season,games,goals,assists,points,shots,hits,timeOnIce,shifts,plusMinus,shotPct,penaltyMinutes,powerPlayGoals,powerPlayPoints,powerPlayTimeOnIce,shortHandedGoals,shortHandedPoints,shortHandedTimeOnIce,gameWinningGoals,overTimeGoals,leagueId,leagueName,teamId,teamName,gameTypeId,poolPoints) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
    insertStats.setInt(1, player.playerId)
    insertStats.setInt(2, stat.season)
    insertStats.setObject(3, stat.gamesPlayed, Types.INTEGER)
    insertStats.setObject(4, stat.goals, Types.INTEGER)
    insertStats.setObject(5, stat.assists, Types.INTEGER)
    insertStats.setObject(6, stat.points, Types.INTEGER)
    insertStats.setObject(7, stat.shots, Types.INTEGER)
    insertStats.setNull(8, Types.INTEGER) // Hits
    insertStats.setObject(9, stat.averageTotalOnIce, Types.FLOAT)
    insertStats.setNull(10, Types.INTEGER) // Shifts
    insertStats.setObject(11, stat.plusMinus, Types.INTEGER)
    insertStats.setObject(12, stat.shootingPctg, Types.INTEGER)
    insertStats.setObject(13, stat.pim, Types.INTEGER)
    insertStats.setObject(14, stat.powerPlayGoals, Types.INTEGER)
    insertStats.setObject(15, stat.powerPlayPoints, Types.INTEGER)
    insertStats.setNull(16, Types.FLOAT) // PowerPlayTimeOnIce
    insertStats.setObject(17, stat.shorthandedGoals, Types.INTEGER)
    insertStats.setObject(18, stat.shorthandedPoints, Types.INTEGER)
    insertStats.setNull(19, Types.FLOAT) // ShorthandedTimeOnIce
    insertStats.setObject(20, stat.gameWinningGoals, Types.INTEGER)
    insertStats.setObject(21, stat.otGoals, Types.INTEGER)
    insertStats.setNull(22, Types.INTEGER) // leagueID
    insertStats.setString(23, stat.leagueAbbrev)
    insertStats.setNull(24, Types.INTEGER) // teamID
    insertStats.setString(25, stat.teamName.default)
    insertStats.setInt(26, stat.gameTypeId)
    insertStats.setFloat(27, poolPoints)
    insertStats.execute()
  }

  fun insertGoalerSeason(playerId: Int, stat: CacheGoalerSeason) {
    val insertStats = connection.prepareStatement("REPLACE INTO PlayersStatsArchiveGoaler (playerId,season,games,gamesStarted,ot,shutouts,wins,losses,timeOnIce,savePercentage,leagueId,leagueName,teamId,teamName,gameTypeId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
    insertStats.setInt(1, playerId)
    insertStats.setInt(2, stat.season)
    insertStats.setObject(3, stat.gamesPlayed, Types.INTEGER)
    insertStats.setObject(4, stat.gamesStarted, Types.INTEGER)
    insertStats.setObject(5, stat.otLosses, Types.INTEGER)
    insertStats.setObject(6, stat.shutouts, Types.INTEGER)
    insertStats.setObject(7, stat.wins, Types.INTEGER)
    insertStats.setObject(8, stat.losses, Types.INTEGER)
    insertStats.setObject(9, stat.averageTotalOnIce, Types.FLOAT)
    insertStats.setObject(10, stat.savePctg, Types.FLOAT)
    insertStats.setNull(11, Types.INTEGER) // league ID
    insertStats.setString(12, stat.leagueAbbrev)
    insertStats.setNull(13, Types.INTEGER) //team id
    insertStats.setString(14, stat.teamName.default)
    insertStats.setInt(15, stat.gameTypeId)
    insertStats.execute()
  }

  // Salary

  fun getPlayerSeasonSalary(season: Season, playerId: Int): PlayerSalarySeason? {
    val rs = statement.executeQuery("SELECT * FROM PlayersSalaries WHERE playerId = $playerId AND season = ${season.intValue}")

    return if(rs.next()) PlayerSalarySeason.fromRow(rs) else null
  }

  fun insertPlayerSalary(playerId: Int, season: Season, salary: Int) {
    val insertSalary = connection.prepareStatement("INSERT INTO PlayersSalaries (playerId,season,avv) VALUES (?,?,?)")
    insertSalary.setInt(1, playerId)
    insertSalary.setInt(2, season.intValue)
    insertSalary.setInt(3, salary)
    insertSalary.execute()
  }

  // Pool

  fun getAllPoolDraftStatuses(): Map<Int, PoolDraftStatut> {
    val statuses = mutableMapOf<Int, PoolDraftStatut>()

    val rs = statement.executeQuery("SELECT * FROM PoolDraft WHERE season = ${Constants.currentSeason.intValue}")

    while (rs.next()) {
      val playerId = rs.getInt("playerId")
      val statutId = rs.getInt("statut")
      val status = PoolDraftStatut.values().firstOrNull { it.value == statutId } ?: PoolDraftStatut.AVAILABLE
      statuses[playerId] = status
    }

    return statuses
  }
}
