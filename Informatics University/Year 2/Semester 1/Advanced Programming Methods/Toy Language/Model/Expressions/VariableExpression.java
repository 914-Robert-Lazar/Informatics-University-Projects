package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.ISymbolTable;
import Model.Values.Value;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(ISymbolTable<String, Value> table) throws MyException {
        return table.findValue(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
