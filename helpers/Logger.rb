require 'colorize'

class Logger
    @@moreLogging = false

    def self.taskTitle(title)
        puts ""
        puts "#####################################################".colorize(:green)
        puts "########     #{title}     ########".colorize(:green)
        puts "#####################################################".colorize(:green)
        puts ""
    end

    def self.taskEnd
        puts ""
        puts "#####################################################".colorize(:green)
        puts ""
    end

    def self.debug(text)
        if @@moreLogging
            logMessage("🟩", text)
        end
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
        puts "#{prefix} [HD] #{text}"
    end
end
