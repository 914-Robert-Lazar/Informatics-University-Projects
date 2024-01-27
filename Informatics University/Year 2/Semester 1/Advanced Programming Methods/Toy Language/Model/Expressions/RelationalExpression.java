package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IHeap;
import Model.Types.BooleanType;
import Model.Types.IntegerType;
import Model.Types.Type;
import Model.Values.BooleanValue;
import Model.Values.IntegerValue;
import Model.Values.Value;

public class RelationalExpression implements Expression {
    Expression expression1, expression2;
    String operation;

    public RelationalExpression(Expression expression1, Expression expression2, String operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value value1 = expression1.evaluate(table, heap);
        if (value1.getType().equals(new IntegerType())) {
            Value value2 = expression2.evaluate(table, heap);
            if (value2.getType().equals(new IntegerType())) {
                IntegerValue i1 = (IntegerValue)value1;
                IntegerValue i2 = (IntegerValue)value2;
                int n1 = i1.getValue(), n2 = i2.getValue();
                switch (operation) {
                    case "<":
                        return new BooleanValue(n1 < n2);
                    case "<=":
                        return new BooleanValue(n1 <= n2);
                    case "==":
                        return new BooleanValue(n1 == n2);
                    case "!=":
                        return new BooleanValue(n1 != n2);
                    case ">":
                        return new BooleanValue(n1 > n2);
                    case ">=":
                        return new BooleanValue(n1 >= n2);
                    default:
                        return new BooleanValue(true);
                }
            }
            else {
                throw new MyException("Second operand is not a integer");
            }
        }
        else {
            throw new MyException("First operand is not a integer");
        }
    }
    
    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);
        if (type1.equals(new IntegerType())) {
            if (type2.equals(new IntegerType())) {
                return new BooleanType();
            }
            else {
                throw new MyException("Second operand is not an integer.");
            }
        }
        else {
            throw new MyException("First operand is not an integer.");
        }
    }
}
