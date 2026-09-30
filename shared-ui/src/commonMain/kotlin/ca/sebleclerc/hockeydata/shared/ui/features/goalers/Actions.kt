package ca.sebleclerc.hockeydata.shared.ui.features.goalers

import ca.sebleclerc.hockeydata.core.domain.PoolDraftStatut
import ca.sebleclerc.hockeydata.core.domain.PoolGoalerPlayer

sealed interface Actions {
  data class OnPlayerAction(
    val player: PoolGoalerPlayer,
    val statut: PoolDraftStatut
  ) : Actions

  data class OnSearchValueChanged(
    val search: String,
  ) : Actions

  // Should find some way to make it generic
  data class DidClickSortValue(
    val value: Boolean,
  ) : Actions
}