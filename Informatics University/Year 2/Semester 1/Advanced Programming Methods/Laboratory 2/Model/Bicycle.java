package Model;

public class Bicycle implements Vehicle {
    String color;

    public Bicycle()
    {
        this.color = "";
    }

    public Bicycle(String color)
    {
        this.color = color;
    }

    @Override
    public String getColor()
    {
        return this.color;
    }

    @Override
    public void setColor(String color)
    {
        this.color = color;
    }

    public String toString()
    {
        return "Bicycle with color: " + this.color;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Bicycle))
        {
            return false;
        }
        else if (((Bicycle) object).getColor().equals(this.color))
        {
            return true;
        }

        return false;
    }
}
