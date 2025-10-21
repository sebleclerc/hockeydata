package ca.sebleclerc.hockeydata.shared.ui.viewmodels

import ca.sebleclerc.hockeydata.core.domain.PoolSkaterPlayer
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.viewmodels.SharedPoolPreviewViewModel

class PoolPreviewViewModel(dbService: DatabaseService) : SharedPoolPreviewViewModel(dbService) {
  var poolSkaters: List<PoolSkaterPlayer> = getAllPoolPreviewPlayers(
    minimal = false,
    sortValue = false
  )

  fun refresh() {
    poolSkaters = getAllPoolPreviewPlayers(
      minimal = false,
      sortValue = false
    )
  }
}