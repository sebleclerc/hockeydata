package ca.sebleclerc.hockeydata.desktop

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ca.sebleclerc.hockeydata.shared.ui.Title

fun main() {
  application {
    val windowState = rememberWindowState(
      width = 600.dp,
      height = 800.dp
    )

    Window(
      onCloseRequest = ::exitApplication,
      state = windowState,
      title = "HockeyData"
    ) {
      MaterialTheme {
        Title()
      }
    }
  }
}