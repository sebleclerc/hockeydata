class Integer
  def show
    return self.to_s.rjust(10)
  end
end

class String
  def intHeader()
    return self.rjust(10).colorize(:yellow)
  end
end
