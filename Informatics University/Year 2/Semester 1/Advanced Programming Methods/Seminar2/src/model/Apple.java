package model;

public class Apple implements Item{
    private float weight;

    public Apple(){
        this(1);
    }
    public Apple(float weight){
        this.weight = weight;
    }
    @Override
    public float getWeight() {
        return this.weight;
    }
}
