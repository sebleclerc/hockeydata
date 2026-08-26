package ca.sebleclerc.hockeydata.shared.ui.pooldata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sebleclerc.hockeydata.cache.CacheService
import ca.sebleclerc.hockeydata.cache.ImportService
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.shared.ui.components.loading.Loading
import ca.sebleclerc.hockeydata.shared.ui.components.loading.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PoolDataViewModel(
  val cacheService: CacheService,
  val dbService: DatabaseService,
  val importService: ImportService,
) : ViewModel(),
  Loading by LoadingViewModel() {
  fun onAction(action: PoolDataAction) {
    when (action) {
      is PoolDataAction.PoolDataRefresh -> poolDataRefresh()
    }
  }

  private fun poolDataRefresh() {
    updateLoading(true)

    viewModelScope.launch(Dispatchers.IO) {
      val players = dbService.getPoolMePlayers()
      val steps = players.map { CacheStep.Player(it.id) }
      cacheService.cache(steps, force = true)
      importService.importPlayers(steps)

      Thread.sleep(500)
      updateLoading(false)
    }
  }
}
