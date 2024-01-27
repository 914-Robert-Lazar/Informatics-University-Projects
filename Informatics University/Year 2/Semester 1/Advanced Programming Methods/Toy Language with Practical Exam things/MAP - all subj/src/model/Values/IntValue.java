package model.Values;
import model.Types.IntType;
import model.Types.Type;


public class IntValue implements Value{
    private final int value;

    public IntValue(int value){
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        return Integer.toString(this.value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValue)) return false;
        IntValue that = (IntValue) o;
        return this.value == that.value;
    }
}
