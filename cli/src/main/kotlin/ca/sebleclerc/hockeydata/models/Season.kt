package ca.sebleclerc.hockeydata.models

class Season(private val value: Int) {
  val intValue: Int
    get() = value

  override fun toString(): String {
    return value.toString()
  }

  val compact: String
    get() {
      val currentStr = value.toString()
      val first = currentStr.take(4).takeLast(2)
      val last = currentStr.takeLast(2)

      return "$first$last"
    }

  val next: Season
    get() {
      val currentStr = value.toString()
      val lastPart = currentStr.takeLast(4)
      val nextYearLast = lastPart.toInt() + 1

      val nextSeason = "$lastPart$nextYearLast"
      return Season(nextSeason.toInt())
    }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Season) return false

    return this.value == other.value
  }
}