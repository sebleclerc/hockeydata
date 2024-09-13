package ca.sebleclerc.hockeydata.helpers

sealed class LoggerColumn(val title: String, val padding: Int) {
  class Custom(title: String, padding: Int) : LoggerColumn(title, padding)
  class ID(title: Int? = null) : LoggerColumn(title?.toString() ?: "ID", Constants.paddingId)
  class Name(name: String? = null) : LoggerColumn(name ?: "Name", Constants.paddingName)
  class Position(value: String? = null) : LoggerColumn(value ?: "Pos", Constants.paddingPosition)
  class Salary(value: String? = null) : LoggerColumn(value ?: "Salary", Constants.paddingAVV)
  class Team(name: String? = null) : LoggerColumn(name ?: "Team", Constants.paddingTeamName)
}