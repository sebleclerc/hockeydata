package ca.sebleclerc.hockeydata.shared.ui.common.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PageLayout(
  title: String,
  actions: (@Composable () -> Unit)? = null,
  listHeader: (@Composable () -> Unit)? = null,
  content: LazyListScope.() -> Unit,
  ) {
  Column {
    PageTitle(title = title)
    if (actions != null) {
      Box(modifier = Modifier.padding(vertical = 8.dp)) {
        actions()
      }
    }
    if (listHeader != null) { listHeader() }
    LazyColumn(
      modifier =
        Modifier
          .fillMaxWidth(),
    ) {
      content()
    }
  }
}