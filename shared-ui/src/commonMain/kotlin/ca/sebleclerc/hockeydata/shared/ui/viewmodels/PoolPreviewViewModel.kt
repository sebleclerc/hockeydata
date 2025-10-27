package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import ca.sebleclerc.hockeydata.core.helpers.Logger
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.viewmodels.SharedPoolPreviewViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PoolPreviewViewModel(
  dbService: DatabaseService,
) : SharedPoolPreviewViewModel(dbService) {
  private val _state = MutableStateFlow(PoolPreviewState())
  val state = _state.asStateFlow()

  init {
    val skaters =
      getAllPoolPreviewPlayers(
        minimal = false,
        sortValue = false,
      )

    _state.update { it.copy(skaterPlayers = skaters) }
  }

//  var poolSkaters: List<PoolSkaterPlayer> =
//    getAllPoolPreviewPlayers(
//      minimal = false,
//      sortValue = false,
//    )
//
//  fun refresh() {
//    poolSkaters =
//      getAllPoolPreviewPlayers(
//        minimal = false,
//        sortValue = false,
//      )
//  }

  fun onAction(action: PoolPreviewAction) {
    Logger.debug("PPVM onAction $action")

    when (action) {
      is PoolPreviewAction.OnPlayerSelect -> {
      }
      is PoolPreviewAction.OnPlayerTaken -> {}
    }
  }
}
