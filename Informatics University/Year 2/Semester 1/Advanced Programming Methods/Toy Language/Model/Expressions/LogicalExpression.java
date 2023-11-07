package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.ISymbolTable;
import Model.Types.BooleanType;
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
    public Value evaluate(ISymbolTable<String, Value> table) throws MyException {
        Value value1 = expression1.evaluate(table);
        if (value1.getType().equals(new BooleanType())) {
            Value value2 = expression2.evaluate(table);
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
}
