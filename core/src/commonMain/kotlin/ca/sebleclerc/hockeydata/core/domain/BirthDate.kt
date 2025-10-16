package ca.sebleclerc.hockeydata.core.domain

data class BirthDate(
  val year: Int,
  val month: Int,
  val day: Int,
  val city: String?,
  val province: String?,
  val country: String?,
) {
  companion object

  val display: String
    get() = "$year-$month-$day"
}
