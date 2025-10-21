package ca.sebleclerc.hockeydata.shared.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.sebleclerc.hockeydata.shared.ui.DI
import ca.sebleclerc.hockeydata.shared.ui.components.PageTitle
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolPreviewViewModel
import ca.sebleclerc.hockeydata.shared.viewmodels.SharedPoolPreviewViewModel

@Composable
fun PoolPreviewScreen(
  viewModel: PoolPreviewViewModel = viewModel { PoolPreviewViewModel(DI.database) }
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, Color.Red)
  ) {
    item { PageTitle("Pool Preview") }
    items(count = viewModel.poolSkaters.size) {
      val player = viewModel.poolSkaters[it]
      Text("Name: ${player.player.fullName}")
    }
  }
}
