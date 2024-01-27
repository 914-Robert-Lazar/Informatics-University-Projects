package model.expressions;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpression implements Expression{
    private Expression expression1;
    private Expression expression2;
    public enum Operand{
        AND,
        OR
    }
    private Operand option;

    public LogicExpression(Expression _ex1,Expression _ex2,Operand _option) throws InterpreterException {
        if (_option!=Operand.OR && _option!=Operand.AND){
            throw new InterpreterException("Invalid option");
        }
        option=_option;
        expression1=_ex1;
        expression2=_ex2;
    }
    @Override
    public Value eval(DictionaryInterface<String, Value> table, HeapInterface<Integer,Value> heap) throws InterpreterException {
        Value v1,v2;
        v1=expression1.eval(table,heap);
        if (v1.getType().equals(new BoolType())){
            v2=expression2.eval(table,heap);
            if (v2.getType().equals(new BoolType())){
                BoolValue bool1=(BoolValue) v1;
                BoolValue bool2=(BoolValue) v2;
                boolean b1,b2;
                b1=bool1.getValue();
                b2= bool2.getValue();
                switch (option) {
                    case AND -> {
                        if (b1 && b2) {
                            return new BoolValue(true);
                        } else {
                            return new BoolValue(false);
                        }
                    }
                    case OR -> {
                        if (b1 || b2) {
                            return new BoolValue(true);
                        } else {
                            return new BoolValue(false);
                        }
                    }
                }
            }else{
                throw new InterpreterException("Sencond operand is not type boolean");
            }
        }else{
            throw new InterpreterException("First operand is not type boolean");
        }
        return new BoolValue();
    }

    @Override
    public Expression deepCopy() throws InterpreterException {
        return new LogicExpression(expression1.deepCopy(),expression2.deepCopy(),option);
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type1,type2;
        type1=expression1.typeCheck(typeEnvironment);
        type2=expression2.typeCheck(typeEnvironment);
        if (type1.equals(new BoolType()))
            if (type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new InterpreterException("Second operand is not of type boolean");
        else
            throw new InterpreterException("First operand is not of type boolean");
    }

    @Override
    public String toString() {
        switch (option) {
            case AND -> {
                return expression1 + " and " + expression2;
            }
            case OR -> {
                return expression1 + " or " + expression2;
            }
        }
        return new String("");
    }
}
