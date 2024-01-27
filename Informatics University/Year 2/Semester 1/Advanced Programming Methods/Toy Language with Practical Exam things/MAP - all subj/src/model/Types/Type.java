package model.Types;
import model.Values.Value;

public interface Type {
    Value getDefault();
    Type deepCopy();
}
