package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IHeap;
import Model.Types.BooleanType;
import Model.Types.Type;
import Model.Values.BooleanValue;
import Model.Values.Value;

public class LogicalExpression  implements Expression{
    Expression expression1, expression2;
    int operation; //1 - and, 2 - or

    public LogicalExpression(Expression expression1, Expression expression2, int operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value value1 = expression1.evaluate(table, heap);
        if (value1.getType().equals(new BooleanType())) {
            Value value2 = expression2.evaluate(table, heap);
            if (value2.getType().equals(new BooleanType())) {
                BooleanValue i1 = (BooleanValue)value1;
                BooleanValue i2 = (BooleanValue)value2;
                boolean n1 = i1.getValue(), n2 = i2.getValue();
                switch (operation) {
                    case 1:
                        return new BooleanValue(n1 && n2);
                    case 2:
                        return new BooleanValue(n1 || n2);
                    default:
                        return new BooleanValue(true);
                }
            }
            else {
                throw new MyException("Second operand is not a boolean");
            }
        }
        else {
            throw new MyException("First operand is not a boolean");
        }
    }

    @Override
    public String toString() {
        String sign = "";
        switch (operation) {
            case 1:
                sign = " && ";
                break;
            case 2:
                sign = " || ";
                break;            
            default:
                break;
        }

        return expression1.toString() + sign + expression2.toString();
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);
        if (type1.equals(new BooleanType())) {
            if (type2.equals(new BooleanType())) {
                return new BooleanType();
            }
            else {
                throw new MyException("Second operand is not a boolean.");
            }
        }
        else {
            throw new MyException("First operand is not a boolean.");
        }
    }
}
