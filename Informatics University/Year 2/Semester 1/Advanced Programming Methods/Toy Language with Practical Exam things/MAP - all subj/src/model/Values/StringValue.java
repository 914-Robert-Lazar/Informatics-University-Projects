package model.Values;

import model.Types.IntType;
import model.Types.StringType;
import model.Types.Type;

import java.util.Objects;

public class StringValue implements Value{
    private final String value;

    public StringValue(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        return this.value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringValue)) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(this.value, that.value);
    }

}
