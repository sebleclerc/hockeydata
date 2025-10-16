package ca.sebleclerc.hockeydata.core.domain

data class Player(
  val id: Int,
  val firstName: String,
  val lastName: String,
  val primaryNumber: Int,
  val birthDate: BirthDate,
  val height: Int,
  val weight: Int,
  val shoot: String,
  val rookie: Boolean,
  val teamId: Int,
  val positionCode: String,
  val headshotUrl: String,
  val heroImageUrl: String,
) {
  companion object

  val fullName: String
    get() {
      return "$firstName $lastName"
    }
}
