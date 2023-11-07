package Model.Types;

public class IntegerType implements Type {
    @Override
    public boolean equals(Object other) {
        return other instanceof IntegerType;
    }

    @Override
    public String toString() {
        return "int";
    }

}
