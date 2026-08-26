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
import ca.sebleclerc.hockeydata.shared.ui.components.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.components.loading.LoadingOverlay
import ca.sebleclerc.hockeydata.shared.ui.navigation.PoolData
import ca.sebleclerc.hockeydata.shared.ui.navigation.PoolMe
import ca.sebleclerc.hockeydata.shared.ui.navigation.PoolPreview
import ca.sebleclerc.hockeydata.shared.ui.navigation.Taken
import ca.sebleclerc.hockeydata.shared.ui.navigation.Teams
import ca.sebleclerc.hockeydata.shared.ui.screens.PoolDataScreen
import ca.sebleclerc.hockeydata.shared.ui.screens.PoolMeScreen
import ca.sebleclerc.hockeydata.shared.ui.screens.PoolPreviewScreen
import ca.sebleclerc.hockeydata.shared.ui.screens.PoolTakenScreen
import ca.sebleclerc.hockeydata.shared.ui.screens.TeamsScreen
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolDataViewModel
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolMeViewModel
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolPreviewViewModel
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolTakenViewModel
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.TeamsViewModel

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
      Button(onClick = { navController.navigate(route = Teams) }) {
        Text("Teams")
      }
      Button(onClick = { navController.navigate(PoolData) }) {
        Text("Data")
      }
      Button(onClick = { navController.navigate(Taken) }) {
        Text("Taken")
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
        val loading by viewModel.loadingState.collectAsStateWithLifecycle()

        LoadingOverlay(
          state = loading,
        ) {
          PoolPreviewScreen(
            state = state,
            onAction = viewModel::onAction,
          )
        }
      }

      composable<Teams> {
        val viewModel: TeamsViewModel = viewModel {
          TeamsViewModel(
            cacheService = DI.cache,
            dbService = DI.database,
            importService = DI.import
          )
        }
        val state by viewModel.state.collectAsStateWithLifecycle()
        val loading by viewModel.loadingState.collectAsStateWithLifecycle()

        LoadingOverlay(
          state = loading,
        ) {
          TeamsScreen(
            state = state,
            onAction = viewModel::onAction,
          )
        }
      }

      composable<PoolData> {
        val viewModel: PoolDataViewModel =
          viewModel {
            PoolDataViewModel(
              cacheService = DI.cache,
              dbService = DI.database,
              importService = DI.import,
            )
          }
        val loading by viewModel.loadingState.collectAsStateWithLifecycle()

        LoadingOverlay(
          state = loading,
        ) {
          PoolDataScreen(
            viewModel = viewModel,
          )
        }
      }

      composable<Taken> {
        val viewModel: PoolTakenViewModel =
          viewModel {
            PoolTakenViewModel(DI.database)
          }
        val loading by viewModel.loadingState.collectAsStateWithLifecycle()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LoadingOverlay(
          state = loading,
        ) {
          PoolTakenScreen(state = state)
        }
      }
    }
  }
}
