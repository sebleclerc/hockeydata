package ca.sebleclerc.hockeydata.helpers

sealed class LoggerColumn(val title: String, val padding: Int) {
  class Custom(title: String, padding: Int) : LoggerColumn(title, padding)

  class ID(title: Int? = null) : LoggerColumn(title?.toString() ?: "ID", Constants.paddingId)
  class Name(name: String? = null) : LoggerColumn(name ?: "Name", Constants.paddingName)
  class Position(value: String? = null) : LoggerColumn(value ?: "Pos", Constants.paddingPosition)
  class Salary(value: String? = null) : LoggerColumn(value ?: "Salary", Constants.paddingAVV)

  class League(name: String? = null) : LoggerColumn(name ?: "League", Constants.paddingLeagueName)
  class Team(name: String? = null) : LoggerColumn(name ?: "Team", Constants.paddingTeamAbbrev)
  class TeamName(name: String? = null) : LoggerColumn(name ?: "Team", Constants.paddingTeamName)

  class Season(season: Int? = null) : LoggerColumn(season?.toString() ?: "Season", Constants.paddingSeason)
  class Games(value: Int? = null) : LoggerColumn(value?.toString() ?: "Games", Constants.paddingInt + 2)
  class Goals(value: Int? = null) : LoggerColumn(value?.toString() ?: "G", Constants.paddingInt)
  class Assists(value: Int? = null) : LoggerColumn(value?.toString() ?: "A", Constants.paddingInt)
  class Points(value: Int? = null) : LoggerColumn(value?.toString() ?: "Pts", Constants.paddingInt)
}