package ca.sebleclerc.hockeydata.helpers

import ca.sebleclerc.hockeydata.core.helpers.Constants

sealed class LoggerColumn(
  val title: String,
  val padding: Int,
) {
  class Custom(
    title: String,
    padding: Int,
  ) : LoggerColumn(title, padding)

  class ID(
    title: Int? = null,
  ) : LoggerColumn(title?.toString() ?: "ID", Constants.PADDING_ID)

  class Name(
    name: String? = null,
  ) : LoggerColumn(name ?: "Name", Constants.PADDING_NAME)

  class Position(
    value: String? = null,
  ) : LoggerColumn(value ?: "Pos", Constants.PADDING_POSITION)

  class Salary(
    value: String? = null,
  ) : LoggerColumn(value ?: "Salary", Constants.PADDING_AVV)

  class League(
    name: String? = null,
  ) : LoggerColumn(name ?: "League", Constants.PADDING_LEAGUE_NAME)

  class Team(
    name: String? = null,
  ) : LoggerColumn(name ?: "Team", Constants.PADDING_TEAM_ABBREV)

  class TeamName(
    name: String? = null,
  ) : LoggerColumn(name ?: "Team", Constants.PADDING_TEAM_NAME)

  class Season(
    season: Int? = null,
  ) : LoggerColumn(season?.toString() ?: "Season", Constants.PADDING_SEASON)

  class Games(
    value: Int? = null,
  ) : LoggerColumn(value?.toString() ?: "Games", Constants.PADDING_INT + 2)

  class Goals(
    value: Int? = null,
  ) : LoggerColumn(value?.toString() ?: "G", Constants.PADDING_INT)

  class Assists(
    value: Int? = null,
  ) : LoggerColumn(value?.toString() ?: "A", Constants.PADDING_INT)

  class Points(
    value: Int? = null,
  ) : LoggerColumn(value?.toString() ?: "Pts", Constants.PADDING_INT)

  class PoolPts(
    value: Float? = null,
  ) : LoggerColumn(value?.toString() ?: "Pool", Constants.PADDING_FLOAT)
}
