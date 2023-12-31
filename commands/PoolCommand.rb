#
# PoolCommand
#

require_relative "./subcommands/PoolMeCommand"
require_relative "./subcommands/PoolPlayerCommand"
require_relative "./subcommands/PoolPreviewCommand"

class PoolCommand < BaseCommand
  desc "me", ""
  subcommand "me", PoolMeCommand

  desc "preview", "Trying to find interesting pool choices."
  subcommand "preview", PoolPreviewCommand

  desc "player", "Related to specific player"
  subcommand "player", PoolPlayerCommand
end
