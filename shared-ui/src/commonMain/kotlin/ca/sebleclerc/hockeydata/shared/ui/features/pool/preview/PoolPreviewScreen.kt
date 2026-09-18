package ca.sebleclerc.hockeydata.shared.ui.features.pool.preview

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
import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.shared.ui.common.components.ToggleButton
import ca.sebleclerc.hockeydata.shared.ui.common.page.PageTitle
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.Actions
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.Header
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.Row
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.RowAction
import ca.sebleclerc.hockeydata.shared.ui.features.pool.common.State
import javax.swing.Action

@Composable
fun PoolPreviewScreen(
  state: State,
  onAction: (Actions) -> Unit,
) {
  var textState by remember {
    mutableStateOf(TextFieldValue(text = ""))
  }

  Column {
    PageTitle("Pool Preview")

    ToggleButton(
      text = "Sort PoolValue",
      onClick = { onAction(Actions.DidClickSortValue(it)) }
    )

    TextField(
      value = textState,
      onValueChange = {
        textState = it
        onAction(Actions.OnSearchValueChanged(it.text))
      },
      modifier =
        Modifier
          .fillMaxWidth()
          .height(50.dp)
          .padding(end = 10.dp),
    )

    Header()
    LazyColumn(
      modifier =
        Modifier
          .fillMaxWidth(),
    ) {
      items(count = state.players.size) {
        val player = state.players[it]
        Row(
          player = player,
          actions = listOf(
            RowAction(
              label = "Taken",
              action = Actions.OnPlayerAction(player, PoolDraftStatut.TAKEN)
            ),
            RowAction(
              label = "ME",
              action = Actions.OnPlayerAction(player, PoolDraftStatut.SELECTED)
            )
          ),
          onAction = onAction
        )
      }
    }
  }
}
