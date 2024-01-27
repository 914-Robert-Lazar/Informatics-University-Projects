package com.example.toylanguage_intellij.Model.Expressions;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IHeap;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.Value;

public class ArithmeticExpression implements Expression{
    Expression expression1, expression2;
    int operation; //1-plus, 2-minus, 3-star, 4-divide

    public ArithmeticExpression(Expression expression1, Expression expression2, int operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value value1, value2;
        value1 = expression1.evaluate(table, heap);
        if (value1.getType().equals(new IntegerType())) {
            value2 = expression2.evaluate(table, heap);
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

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);
        if (type1.equals(new IntegerType())) {
            if (type2.equals(new IntegerType())) {
                return new IntegerType();
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
