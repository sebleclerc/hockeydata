package ca.sebleclerc.hockeydata.shared.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ca.sebleclerc.hockeydata.shared.ui.components.PoolMeHeader
import ca.sebleclerc.hockeydata.shared.ui.components.PoolMeRow
import ca.sebleclerc.hockeydata.shared.ui.components.PoolMeSalary
import ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay.EmptyRow
import ca.sebleclerc.hockeydata.shared.ui.components.page.PageTitle
import ca.sebleclerc.hockeydata.shared.ui.viewmodels.PoolMeState

@Composable
fun PoolMeScreen(
  state: PoolMeState
) {
  Column{
    PageTitle("Pool Me")
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      item {
        PoolMeHeader()
      }

      items(state.forwards.count()) {
        val player = state.forwards[it]
        PoolMeRow(player)
      }

      item {
        EmptyRow()
        PoolMeHeader()
      }

      items(state.defenses.count()) {
        val player = state.defenses[it]
        PoolMeRow(player)
      }

      item {
        EmptyRow()
        PoolMeSalary(state.salary)
      }
    }
  }
}
