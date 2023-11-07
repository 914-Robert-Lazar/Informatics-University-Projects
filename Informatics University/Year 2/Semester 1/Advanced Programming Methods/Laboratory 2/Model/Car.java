package Model;

public class Car implements Vehicle {
    String color;

    public Car()
    {
        this.color = "";
    }

    public Car(String color)
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
        return "Car with color: " + this.color;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Car))
        {
            return false;
        }
        else if (((Car) object).getColor().equals(this.color))
        {
            return true;
        }

        return false;
    }
}
