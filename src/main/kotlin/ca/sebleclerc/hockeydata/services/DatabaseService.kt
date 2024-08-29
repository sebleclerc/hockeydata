package ca.sebleclerc.hockeydata.services

import com.andreapivetta.kolor.yellow
import java.sql.DriverManager
import java.util.Properties

private val url = "jdbc:mariadb://127.0.0.1:3306/hockeydata"
private val username = "sleclerc"
private val password = "sleclerc"

class DatabaseService {
  fun some() {
    println("Name".padStart(30, ' ').yellow())
    Class.forName("org.mariadb.jdbc.Driver")

    val props = Properties()
    props.setProperty("user", username)
    props.setProperty("password", password)

    DriverManager.getConnection(url, props).use { connection ->
      connection.createStatement().use { st ->
        st.executeQuery("SELECT * FROM Teams").use { rs ->
          while (rs.next()) {
            val name = rs.getString("name")
            println(name.padStart(30, ' '))
          }
        }
      }
    }
  }
}