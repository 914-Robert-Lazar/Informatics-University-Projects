package model.expressions;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression{

    private Expression expression1;
    private Expression expression2;
    public enum Operand{
        ADD,
        SUB,
        MUL,
        DIV

    }
    private Operand operand;

    public ArithmeticExpression(Expression _e1,Expression _e2,Operand _operand) throws InterpreterException{
//        if (_operand!='+' || _operand!='-' || _operand!='*' || _operand!='/')
//            throw new InterpreterException("Option not valid");
        operand=_operand;
        expression1=_e1;
        expression2=_e2;
    }




    @Override
    public Value eval(DictionaryInterface<String, Value> table, HeapInterface<Integer,Value> heap) throws InterpreterException {
        Value v1,v2;
        v1=expression1.eval(table,heap);
        if (v1.getType().equals(new IntType())){
            v2=expression2.eval(table,heap);
            if (v2.getType().equals(new IntType())){
                IntValue int1=(IntValue) v1;
                IntValue int2=(IntValue) v2;
                int n1,n2;
                n1=int1.getValue();
                n2= int2.getValue();
                switch (operand) {
                    case ADD -> {
                        return new IntValue(n1 + n2);
                    }
                    case SUB -> {
                        return new IntValue(n1 - n2);
                    }
                    case MUL -> {
                        return new IntValue(n1 * n2);
                    }
                    case DIV -> {
                        if (n2 == 0)
                            throw new InterpreterException("division by zero");
                        return new IntValue(n1 / n2);
                    }
                }
            }else {
                throw new InterpreterException("Second operand is not an integer");
            }
        }else{
            throw new InterpreterException("First operand is not an integer");
        }
        return new IntValue();
    }

    @Override
    public Expression deepCopy() throws InterpreterException {
        return new ArithmeticExpression(expression1.deepCopy(),expression2.deepCopy(),operand);
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type1,type2;
        type1=expression1.typeCheck(typeEnvironment);
        type2=expression2.typeCheck(typeEnvironment);
        if (type1.equals(new IntType()))
            if (type2.equals(new IntType()))
                return new IntType();
            else
                throw new InterpreterException("Second operand is not an integer");
        else
            throw new InterpreterException("First operand is not an integer");
    }

    @Override
    public String toString() {
        switch (operand) {
            case ADD -> {
                return expression1 + "+" + expression2;
            }
            case SUB -> {
                return expression1 + "-" + expression2;
            }
            case MUL -> {
                return expression1 + "*" + expression2;
            }
            case DIV -> {
                return expression1 + "/" + expression2;
            }
        }
        return new String("");
    }
}
