package ca.sebleclerc.hockeydata.shared.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.sebleclerc.hockeydata.shared.ui.navigation.PoolMe
import ca.sebleclerc.hockeydata.shared.ui.navigation.PoolPreview
import ca.sebleclerc.hockeydata.shared.ui.screens.PoolMeScreen
import ca.sebleclerc.hockeydata.shared.ui.screens.PoolPreviewScreen
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolMeViewModel
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolPreviewViewModel

@Composable
fun HockeyDataApp(navController: NavHostController = rememberNavController()) {
  Column(
    modifier =
      Modifier
        .padding(horizontal = 10.dp),
  ) {
    NavigationBar {
      Button(onClick = { navController.navigate(PoolMe) }) {
        Text("ME")
      }
      Button(onClick = { navController.navigate(PoolPreview) }) {
        Text("Preview")
      }
    }
    NavHost(
      navController = navController,
      startDestination = PoolMe,
      modifier = Modifier,
    ) {
      composable<PoolMe> {
        val viewModel: PoolMeViewModel = viewModel { PoolMeViewModel(DI.database) }
        val state by viewModel.state.collectAsStateWithLifecycle()

        PoolMeScreen(
          state = state,
        )
      }

      composable<PoolPreview> {
        val viewModel: PoolPreviewViewModel = viewModel { PoolPreviewViewModel(DI.database) }
        val state by viewModel.state.collectAsStateWithLifecycle()

        PoolPreviewScreen(
          state = state,
          onAction = viewModel::onAction,
        )
      }
    }
  }
}
