package ca.sebleclerc.hockeydata.helpers

sealed class LoggerColumn(val title: String, val padding: Int) {
  class Custom(title: String, padding: Int) : LoggerColumn(title, padding)
  class ID(title: Int? = null) : LoggerColumn(title?.toString() ?: "ID", Constants.paddingId)
  class Name(name: String? = null) : LoggerColumn(name ?: "Name", Constants.paddingName)
}