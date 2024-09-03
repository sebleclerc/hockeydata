package ca.sebleclerc.hockeydata.commands

import ca.sebleclerc.hockeydata.DI
import com.github.ajalt.clikt.core.CliktCommand

abstract class BaseCommand(val di: DI, name: String) : CliktCommand(name = name) {}