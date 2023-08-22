
# Couleur dans le texte
# https://github.com/fazibear/colorize

class Logger
    def self.debug(text)
        logMessage("🟩", text)
    end

    def self.info(text)
        logMessage("🔷", text)
    end

    def self.warning(text)
        logMessage("⚠️ ", text)
    end

    def self.error(text)
        logMessage("❗", text)
    end

    private

    def self.logMessage(prefix, text)
        puts "#{prefix} [HockeyData] #{text}"
    end
end