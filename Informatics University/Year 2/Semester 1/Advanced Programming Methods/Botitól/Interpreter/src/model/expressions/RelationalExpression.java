package model.expressions;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression{
    private Expression expression1;
    private Expression expression2;
    private Operand operand;
    public RelationalExpression(Expression ex1, Expression ex2, Operand operand ){
        expression1=ex1;
        expression2=ex2;
        this.operand=operand;
    }
    public enum Operand{
        LESS,
        LESSOREQUAL,
        EQUAL,
        NOTEQUAL,
        GREATER,
        GREATEROREQUAL
    }
    @Override
    public Value eval(DictionaryInterface<String, Value> table, HeapInterface<Integer,Value> heap) throws InterpreterException {
        Value v1,v2;
        v1=expression1.eval(table,heap);
        if ((v1.getType().equals(new IntType()))){
            IntValue value1=(IntValue)v1;
            v2=expression2.eval(table,heap);
            if (v2.getType().equals(new IntType())){
                IntValue value2=(IntValue)v2;
                switch (operand){
                    case LESS -> {
                        return new BoolValue(value1.getValue()<value2.getValue());
                    }
                    case EQUAL -> {
                        return new BoolValue(value1.getValue()==value2.getValue());
                    }
                    case GREATER -> {
                        return new BoolValue(value1.getValue()>value2.getValue());
                    }
                    case NOTEQUAL -> {
                        return new BoolValue(value1.getValue()!=value2.getValue());
                    }
                    case LESSOREQUAL -> {
                        return new BoolValue(value1.getValue()<=value2.getValue());
                    }
                    case GREATEROREQUAL -> {
                        return new BoolValue(value1.getValue()>=value2.getValue());
                    }
                }
            }else{
                throw new InterpreterException("Second expression is not of type int");
            }
        }else{
            throw new InterpreterException("First expression is not of type int");
        }
        return null;
    }

    @Override
    public String toString() {
        switch (operand){
            case LESS -> {
                return expression1+"<"+expression2;
            }
            case GREATER -> {
                return expression1+">"+expression2;
            }
            case EQUAL -> {
                return expression1+"=="+expression2;
            }
            case GREATEROREQUAL -> {
                return expression1+">="+expression2;
            }
            case LESSOREQUAL -> {
                return expression1+"<="+expression2;
            }
            case NOTEQUAL -> {
                return expression1+"!="+expression2;
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    public Expression deepCopy() throws InterpreterException {
        return new RelationalExpression(expression1.deepCopy(),expression2.deepCopy(),operand);
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type1,type2;
        type1=expression1.typeCheck(typeEnvironment);
        type2=expression2.typeCheck(typeEnvironment);
        if (type1.equals(new IntType()))
            if (type2.equals(new IntType()))
                return new BoolType();
            else
                throw new InterpreterException("Second operand is not an integer");
        else
            throw new InterpreterException("First operand is not an integer");
    }
}
