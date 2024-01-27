package model.values;

import model.types.StringType;
import model.types.Type;

import java.util.Objects;

public class StringValue implements Value{

    private String value;

    public StringValue(String string){
        this.value=string;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StringValue))
            return false;
        return Objects.equals(((StringValue) obj).getValue(), value);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }
    public String getValue(){
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
