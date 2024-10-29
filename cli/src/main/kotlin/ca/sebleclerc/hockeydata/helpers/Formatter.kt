package ca.sebleclerc.hockeydata.helpers

object Formatter {
  fun intToSalary(salary: Int): String {
    var formatted = when {
      salary < 1000000 -> StringBuilder(salary.toString())
        .insert(3, ' ').toString()
      salary < 10000000 -> StringBuilder(salary.toString())
        .insert(1, ' ')
        .insert(5, ' ')
        .toString()
      salary >= 10000000 -> StringBuilder(salary.toString())
        .insert(2, ' ')
        .insert(6, ' ')
        .toString()
      else -> "0"
    }

    formatted += " $"

    return formatted
  }
}