package ca.sebleclerc.hockeydata.shared.ui.components.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PageLayout(title: String, header: (@Composable () -> Unit)? = null, content: LazyListScope.() -> Unit,) {
  Column {
    PageTitle(title = title)
    if (header != null) { header() }
    LazyColumn(
      modifier =
        Modifier
          .fillMaxWidth(),
    ) {
      content()
    }
  }
}