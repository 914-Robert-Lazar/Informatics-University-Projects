package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement{
    private String name;
    private Expression expression;
    public ReadFileStatement(String name,Expression exp){
        this.name=name;
        this.expression=exp;

    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException{
        if (!(state.getSymbolsTable().isDefined(name))){
            throw new InterpreterException("Name is not defined");
        }
        if (!(state.getSymbolsTable().lookup(name).getType().equals(new IntType()))){
            throw new InterpreterException("Name is not bound to int");
        }
        Value value=expression.eval(state.getSymbolsTable(), state.getHeapTable());
        if (!(value instanceof StringValue)){
            throw new InterpreterException("Value is not of StringValue");
        }
        if (!(state.getFileTable().isDefined((StringValue) value))){
            throw new InterpreterException("BufferReader is not defined");
        }
        BufferedReader buf=state.getFileTable().lookup((StringValue) value);
        try {
            String line = buf.readLine();
            if (line==null){
                state.getSymbolsTable().update(name,new IntType().defaultValue());
            }
            else{
                state.getSymbolsTable().update(name,new IntValue(Integer.parseInt(line)));
            }
        }catch (IOException ie){
            throw new InterpreterException("Cannot read from the file");
        }
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new ReadFileStatement(name,expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type variableType=typeEnvironment.lookup(name);
        if (variableType.equals(new IntType())){
            Type expressionType=expression.typeCheck(typeEnvironment);
            if (expressionType.equals(new StringType()))
                return typeEnvironment;
            else
                throw new InterpreterException("Read statement: expression is not of type string");
        }else
            throw new InterpreterException("Read statement: variable is not of type int");
    }

    @Override
    public String toString() {
        return "ReadFile("+name+")"+";";
    }
}
