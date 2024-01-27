package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IHeap;
import Model.Types.Type;
import Model.Values.Value;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        return table.findValue(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.findValue(id);
    }
}
