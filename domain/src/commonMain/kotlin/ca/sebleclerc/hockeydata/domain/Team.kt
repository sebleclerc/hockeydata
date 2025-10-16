package ca.sebleclerc.hockeydata.domain

data class Team(
  val id: Int,
  val name: String,
  val venue: String,
  val abbreviation: String,
  val firstYearOfPlay: String,
  val divisionId: Int,
  val conferenceId: Int,
  val franchiseId: Int,
  val active: Boolean,
) {
  companion object
}
