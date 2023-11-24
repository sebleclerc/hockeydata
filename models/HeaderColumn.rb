class HeaderColumn
  attr_accessor :title, :adjust

  def self.id()
    header = HeaderColumn.new
    header.title = "ID"
    header.adjust = Constants.idPadding

    return header
  end

  def self.name()
    header = HeaderColumn.new
    header.title = "Name"
    header.adjust = Constants.fullNamePadding

    return header
  end

  def self.custom(title, adjust)
    header = HeaderColumn.new
    header.title = title
    header.adjust = adjust

    return header
  end
end
