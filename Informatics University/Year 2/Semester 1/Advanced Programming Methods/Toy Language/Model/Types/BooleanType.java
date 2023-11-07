package Model.Types;

public class BooleanType implements Type{
    @Override
    public boolean equals(Object other) {
        return other instanceof BooleanType;
    }

    @Override
    public String toString() {
        return "boolean";
    }
}
