package ca.sebleclerc.hockeydata.desktop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ca.sebleclerc.hockeydata.shared.ui.PageTitle
import ca.sebleclerc.hockeydata.shared.ui.Title

fun main() {
  application {
    val windowState =
      rememberWindowState(
        width = 600.dp,
        height = 800.dp,
      )

    Window(
      onCloseRequest = ::exitApplication,
      state = windowState,
      title = "HockeyData",
    ) {
      MaterialTheme {
        FlowColumn(
          modifier =
            Modifier
              .padding(30.dp)
              .fillMaxSize()
              .border(width = 1.dp, color = Color.Red),
        ) {
          PageTitle("Hockey Data")
          Title()
          Title()
          Title()
        }
      }
    }
  }
}
