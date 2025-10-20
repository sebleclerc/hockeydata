package ca.sebleclerc.hockeydata.shared.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.sebleclerc.hockeydata.shared.ui.navigation.PoolMe
import ca.sebleclerc.hockeydata.shared.ui.navigation.PoolPreview
import ca.sebleclerc.hockeydata.shared.ui.screens.PoolMeScreen
import ca.sebleclerc.hockeydata.shared.ui.screens.PoolPreviewScreen

@Composable
fun HockeyDataApp(
  navController: NavHostController = rememberNavController()
) {
  Column(
    modifier = Modifier
      .padding(horizontal = 10.dp)
  ) {
    NavigationBar {
      Button(onClick = { navController.navigate(PoolMe) }) {
        Text("PoolMe")
      }
      Button(onClick = { navController.navigate(PoolPreview) }) {
        Text("PoolPreview")
      }
    }
    NavHost(
      navController = navController,
      startDestination = PoolPreview,
      modifier = Modifier
    ) {
      composable<PoolMe> { PoolMeScreen() }
      composable<PoolPreview>{
        PoolPreviewScreen()
      }
    }
  }
}