package ca.sebleclerc.hockeydata.shared.ui.components.loading

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface Loading {
  val loadingState: StateFlow<LoadingState>
  fun updateLoading(isLoading: Boolean)
}

class LoadingViewModel : Loading {
  private val _loadingState = MutableStateFlow(LoadingState())
  override val loadingState = _loadingState.asStateFlow()

  override fun updateLoading(isLoading: Boolean) {
    _loadingState.update {
      it.copy(
        isLoading = isLoading,
      )
    }
  }
}