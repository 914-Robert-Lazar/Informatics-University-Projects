package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IHeap;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExpression implements Expression{
    Value e;

    public ValueExpression(Value e) {
        this.e = e;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        return e;
    }
    
    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
