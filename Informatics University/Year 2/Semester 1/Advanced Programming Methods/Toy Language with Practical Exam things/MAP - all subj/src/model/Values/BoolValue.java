package model.Values;
import model.Types.BoolType;
import model.Types.Type;

public class BoolValue implements Value{
    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        return Boolean.toString(this.value);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoolValue)) return false;
        BoolValue that = (BoolValue) o;
        return this.value == that.value;
    }
}
