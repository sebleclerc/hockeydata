package ca.sebleclerc.hockeydata.shared.ui.pooldata

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageTitle

@Composable
fun PoolDataScreen(viewModel: PoolDataViewModel) {
  Column {
    PageTitle("Pool Data")

    Column {
      Button(
        onClick = { viewModel.onAction(PoolDataAction.PoolDataRefresh) },
      ) {
        Text("Update Pool Data")
      }
    }
  }
}
