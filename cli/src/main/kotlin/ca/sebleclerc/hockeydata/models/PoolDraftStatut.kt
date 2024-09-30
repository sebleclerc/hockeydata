package ca.sebleclerc.hockeydata.models

enum class PoolDraftStatut(val value: Int) {
  AVAILABLE(0),
  SELECTED(1),
  TAKEN(2),
  EXCHANGED(3),
  REVOKED(4)
}