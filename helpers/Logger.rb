require 'colorize'

class Logger
    @@moreLogging = true

    def self.taskTitle(title)
        info ""
        info "###########################################################".colorize(:green)
        info "########     #{title}     ########".colorize(:green)
        info "###########################################################".colorize(:green)
        info ""
        info ""
    end

    def self.taskEnd
        info ""
        info ""
        info "###########################################################".colorize(:green)
        info ""
    end

    def self.header(columns)
        header = ""

        columns.each do |column|
            header += column.title.rjust(column.adjust).colorize(:yellow)
        end

        info header
    end

    def self.row(columns)
        line = ""

        columns.each do |column|
            line += column.title.rjust(column.adjust)
        end

        info line
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
