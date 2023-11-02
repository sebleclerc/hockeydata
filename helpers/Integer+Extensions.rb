class Integer
  def show
    return self.to_s.rjust(10)
  end

  def showSalary
    if self < 1000000
      show = self.to_s
      .insert(3, ' ')
      .rjust(12)
    elsif self < 10000000
      show = self.to_s
      .insert(1, ' ')
      .insert(5, ' ')
      .rjust(12)
    else
      show = self.to_s
      .insert(2, ' ')
      .insert(6, ' ')
      .rjust(12)
    end

    show += " $"

    return show#
  end

end

class String
  def intHeader()
    return self.rjust(10)
  end
end
