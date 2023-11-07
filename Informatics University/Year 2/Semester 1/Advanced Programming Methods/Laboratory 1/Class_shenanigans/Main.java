class Element
{
    int id;
    String name;
    static int counter = 0;

    public Element()
    {
        this.id = 0;
        this.name = "undefined";
        counter++;
    }

    public Element(int id)
    {
        this.id = id;
        this.name = "undefined";
        counter++;
    }

    public Element(int id, String name)
    {
        this.id = id;
        this.name = name;
        counter++;
    }

    public String toString()
    {
        return this.id + " " + this.name + " with counter " + this.counter;
    }

    public void saysHello()
    {
        System.out.println(this.toString() + " says hello as element.");
    }
}

class ColoredElement extends Element
{
    String color;
    public ColoredElement(int id, String name, String color)
    {
        super(id, name);
        this.color = color;
    }

    public void saysHello()
    {
        System.out.println(this.toString() + " says hello as colored element.");
    }
}

public class Main {
    public static void main(String args[])
    {
        Element element1 = new Element();
        System.out.println(element1);
        Element element2 = new Element(5);
        System.out.println(element2);
        Element element3 = new Element(5, "Hi");
        element3.saysHello();
        
        ColoredElement coloredElement = new ColoredElement(4, "Hello", "Red");
        coloredElement.saysHello();

        try
        {
            int a = 5, b = 0;
            System.out.println(a / b);
        }
        catch (ArithmeticException e)
        {
            e.printStackTrace();
        }
    }
}
