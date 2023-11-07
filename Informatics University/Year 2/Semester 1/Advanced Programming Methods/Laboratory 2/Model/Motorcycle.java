package Model;

public class Motorcycle implements Vehicle {
    String color;

    public Motorcycle()
    {
        this.color = "";
    }

    public Motorcycle(String color)
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
        return "Motorcycle with color: " + this.color;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Motorcycle))
        {
            return false;
        }
        else if (((Motorcycle) object).getColor().equals(this.color))
        {
            return true;
        }

        return false;
    }
}
