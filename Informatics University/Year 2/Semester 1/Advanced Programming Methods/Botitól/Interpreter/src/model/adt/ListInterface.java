package model.adt;

import model.expressions.Expression;
import model.statements.VariableDeclarationStatement;
import model.values.Value;

import java.util.List;

public interface ListInterface<T> {
    void add(T value);
    List<T> getList();

}
