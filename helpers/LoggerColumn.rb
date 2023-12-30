class LoggerColumn
  attr_accessor :title, :adjust

  def self.id(id=nil)
    column = LoggerColumn.new
    column.title = id.nil? ? "ID" : id.to_s
    column.adjust = Constants.idPadding

    return column
  end

  def self.name(value=nil)
    column = LoggerColumn.new
    column.title = value.nil? ? "Name" : value
    column.adjust = Constants.fullNamePadding

    return column
  end

  def self.custom(title, adjust)
    column = LoggerColumn.new
    column.title = title
    column.adjust = adjust

    return column
  end

  def self.int(title, value=nil)
    column = LoggerColumn.new
    column.title = value.nil? ? title : value.to_s
    column.adjust = Constants.intPadding

    return column
  end

  def self.float(title, value=nil)
    column = LoggerColumn.new
    column.title = value.nil? ? title : value.to_s
    column.adjust = Constants.floatPadding

    return column
  end
end
