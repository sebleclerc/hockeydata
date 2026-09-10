package ca.sebleclerc.hockeydata.shared.ui.features.poolme

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ca.sebleclerc.hockeydata.shared.ui.common.lazydisplay.EmptyRow
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageLayout

@Composable
fun PoolMeScreen(state: PoolMeState, onClick: (Actions) -> Unit) {
  Column {
    PageLayout(
      title = "Pool Me",
      toolbar = { Toolbar(onClick = onClick) },
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
        PoolMePoolPoints(state.poolPoints)
        PoolMeSalary(state.salary)
      }
    }
  }
}
