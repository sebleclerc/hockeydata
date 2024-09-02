package ca.sebleclerc.hockeydata.services

import ca.sebleclerc.hockeydata.models.Player
import ca.sebleclerc.hockeydata.models.Team
import ca.sebleclerc.hockeydata.models.fromResult
import ca.sebleclerc.hockeydata.models.fromRow
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
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

  fun getRosterForTeam(teamId: Int): List<Int> {
    val players = mutableListOf<Int>()

    val rs = statement.executeQuery("SELECT * FROM TeamsPlayers WHERE teamId = $teamId")
    while (rs.next()) { players.add(rs.getInt("playerId")) }

    return players
  }

  // Players
  fun getPlayerForId(playerId: Int): Player? {
    val rs = statement.executeQuery("SELECT * FROM Players WHERE id = $playerId")

    return if (rs.next()) {
      val player = Player.fromRow(rs)
      player
    } else {
      null
    }
  }
}