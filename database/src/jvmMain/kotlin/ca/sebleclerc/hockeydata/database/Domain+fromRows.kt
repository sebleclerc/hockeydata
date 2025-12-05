@file:Suppress("ktlint:standard:filename")

package ca.sebleclerc.hockeydata.database

import ca.sebleclerc.hockeydata.core.domain.BirthDate
import ca.sebleclerc.hockeydata.core.domain.Player
import ca.sebleclerc.hockeydata.core.domain.PlayerSkaterSeason
import ca.sebleclerc.hockeydata.core.domain.Season
import ca.sebleclerc.hockeydata.core.domain.Team
import java.sql.ResultSet

// region BirthDate

fun BirthDate.Companion.fromRow(rs: ResultSet): BirthDate =
  BirthDate(
    rs.getInt("birthYear"),
    rs.getInt("birthMonth"),
    rs.getInt("birthDay"),
    rs.getString("birthCity"),
    rs.getString("birthProvince"),
    rs.getString("birthCountry"),
  )

// endregion

// region Player

fun Player.Companion.fromRow(rs: ResultSet): Player =
  Player(
    rs.getInt("id"),
    rs.getString("firstName"),
    rs.getString("lastName"),
    rs.getInt("primaryNumber"),
    BirthDate.fromRow(rs),
    rs.getInt("height"),
    rs.getInt("weight"),
    rs.getString("shoot"),
    rs.getBoolean("rookie"),
    rs.getInt("teamId"),
    rs.getString("positionCode"),
    rs.getString("headshotUrl"),
    rs.getString("heroImageUrl"),
  )

// endregion

// region PlayerSkaterSeason

fun PlayerSkaterSeason.Companion.fromRow(rs: ResultSet): PlayerSkaterSeason =
  PlayerSkaterSeason(
    Season(rs.getInt("season")),
    rs.getString("leagueName"),
    rs.getString("teamName"),
    rs.getInt("games"),
    rs.getInt("goals"),
    rs.getInt("assists"),
    rs.getInt("points"),
    rs.getFloat("poolPoints"),
  )

// endregion

// region Team

fun Team.Companion.fromResult(rs: ResultSet): Team =
  Team(
    rs.getInt("id"),
    rs.getString("name"),
    rs.getString("venue"),
    rs.getString("abbreviation"),
    rs.getString("firstYearOfPlay"),
    rs.getInt("divisionId"),
    rs.getInt("conferenceId"),
    rs.getInt("franchiseId"),
    rs.getBoolean("active"),
  )

// endregion
