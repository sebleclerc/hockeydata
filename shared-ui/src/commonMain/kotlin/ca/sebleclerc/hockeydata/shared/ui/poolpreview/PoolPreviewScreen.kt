package ca.sebleclerc.hockeydata.shared.ui.poolpreview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageTitle

@Composable
fun PoolPreviewScreen(
  state: PoolPreviewState,
  onAction: (PoolPreviewAction) -> Unit,
) {
  var textState by remember {
    mutableStateOf(TextFieldValue(text = ""))
  }

  Column {
    PageTitle("Pool Preview")

    TextField(
      value = textState,
      onValueChange = {
        textState = it
        onAction(PoolPreviewAction.OnSearchValueChanged(it.text))
      },
      modifier =
        Modifier
          .fillMaxWidth()
          .height(50.dp)
          .padding(end = 10.dp),
    )

    PoolPreviewHeader()
    LazyColumn(
      modifier =
        Modifier
          .fillMaxWidth(),
    ) {
      items(count = state.filteredPlayers.size) {
        val player = state.filteredPlayers[it]
        PoolPreviewRow(player, onAction)
      }
    }
  }
}
