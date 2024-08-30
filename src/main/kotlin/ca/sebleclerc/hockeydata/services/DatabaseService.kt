package ca.sebleclerc.hockeydata.services

import ca.sebleclerc.hockeydata.models.Team
import ca.sebleclerc.hockeydata.models.fromResult
import com.andreapivetta.kolor.yellow
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

private const val url = "jdbc:mariadb://127.0.0.1:3306/hockeydata"
private const val username = "sleclerc"
private const val password = "sleclerc"

class DatabaseService {
  private val connection: Connection

  init {
    Class.forName("org.mariadb.jdbc.Driver")

    val props = Properties()
    props.setProperty("user", username)
    props.setProperty("password", password)

    connection = DriverManager.getConnection(url, props)
  }

  fun getAllTeams(): List<Team> {
    val teams = mutableListOf<Team>()

    connection.createStatement().use { st ->
      st.executeQuery("SELECT * FROM Teams").use { rs ->
        while (rs.next()) { teams.add(Team.fromResult(rs)) }
      }
    }

    return teams
  }
}