package Model;

public class Undefined implements Vehicle{

    @Override
    public String getColor() {
        return new String();
    }

    @Override
    public void setColor(String color) {

    }
    
    @Override
    public boolean equals(Object object)
    {
        return false;
    }
}
