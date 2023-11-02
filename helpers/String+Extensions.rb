class String
  def showHeader
    return self.rjust(25).colorize(:yellow)
  end

  def show
    return self.rjust(25)
  end
end
