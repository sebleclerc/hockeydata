package ca.sebleclerc.hockeydata.shared.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.core.helpers.Formatter
import ca.sebleclerc.hockeydata.shared.ui.components.lazydisplay.RowItem

@Composable
fun PoolMeSalary(salary: Int) {
  Row(
    modifier =
      Modifier
        .height(Constants.UI_ROW_HEIGHT.dp),
  ) {
    RowItem(
      text = "       Salaire total:    ${Formatter.intToSalary(salary)}",
      padding = 500,
    )
  }
}
