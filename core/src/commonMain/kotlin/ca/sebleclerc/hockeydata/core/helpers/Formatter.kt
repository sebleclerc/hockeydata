package ca.sebleclerc.hockeydata.core.helpers

import java.math.BigDecimal
import java.math.RoundingMode

object Formatter {
  fun roundDouble(value: Double): String {
    if (value == 0.0 || value.isNaN() || value.isInfinite()) {
      return "0.00"
    }

    return BigDecimal(value)
      .setScale(2, RoundingMode.HALF_EVEN)
      .toString()
  }


  fun intToSalary(salary: Int): String {
    var formatted =
      when {
        salary == 0 -> "0"
        salary < 1000000 ->
          StringBuilder(salary.toString())
            .insert(3, ' ')
            .toString()
        salary < 10000000 ->
          StringBuilder(salary.toString())
            .insert(1, ' ')
            .insert(5, ' ')
            .toString()
        salary >= 10000000 ->
          StringBuilder(salary.toString())
            .insert(2, ' ')
            .insert(6, ' ')
            .toString()
        else -> "0"
      }

    formatted += " $"

    return formatted
  }
}
