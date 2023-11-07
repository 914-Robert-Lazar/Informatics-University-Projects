package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.ISymbolTable;
import Model.Types.IntegerType;
import Model.Values.IntegerValue;
import Model.Values.Value;

public class ArithmeticExpression implements Expression{
    Expression expression1, expression2;
    int operation; //1-plus, 2-minus, 3-star, 4-divide

    public ArithmeticExpression(Expression expression1, Expression expression2, int operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(ISymbolTable<String, Value> table) throws MyException {
        Value value1, value2;
        value1 = expression1.evaluate(table);
        if (value1.getType().equals(new IntegerType())) {
            value2 = expression2.evaluate(table);
            if (value2.getType().equals(new IntegerType())) {
                IntegerValue i1 = (IntegerValue)value1;
                IntegerValue i2 = (IntegerValue)value2;
                int n1 = i1.getValue(), n2 = i2.getValue();
                switch (operation) {
                    case 1:
                        return new IntegerValue(n1 + n2);
                    case 2:
                        return new IntegerValue(n1 - n2);
                    case 3:
                        return new IntegerValue(n1 * n2);
                    case 4:
                    {
                        if (n2 == 0) {
                            throw new MyException("Division by zero");
                        }
                        else {
                            return new IntegerValue(n1 / n2);
                        }
                    } 
                    default:
                        return new IntegerValue(0);
                }
            }
            else {
                throw new MyException("Second operand is not an integer");
            }
        }
        else {
            throw new MyException("First operand is not an integer");
        }
    }

    @Override
    public String toString() {
        String sign = "";
        switch (operation) {
            case 1:
                sign = " + ";
                break;
            case 2:
                sign = " - ";
                break;
            case 3:
                sign = " * ";
                break;
            case 4:
                sign = " / ";
                break;
            default:
                break;
        }

        return expression1.toString() + sign + expression2.toString();
    }
}
