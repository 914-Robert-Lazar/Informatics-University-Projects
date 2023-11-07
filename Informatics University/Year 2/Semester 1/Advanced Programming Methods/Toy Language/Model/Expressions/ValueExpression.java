package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.ISymbolTable;
import Model.Values.Value;

public class ValueExpression implements Expression{
    Value e;

    public ValueExpression(Value e) {
        this.e = e;
    }

    @Override
    public Value evaluate(ISymbolTable<String, Value> table) throws MyException {
        return e;
    }
    
    @Override
    public String toString() {
        return e.toString();
    }
}
