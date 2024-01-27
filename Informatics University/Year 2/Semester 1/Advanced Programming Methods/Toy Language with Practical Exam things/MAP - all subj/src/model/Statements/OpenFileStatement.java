package model.Statements;

import exceptions.FileException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.StringType;
import model.Types.Type;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileStatement implements StatementInterface{
    private final Expression nameOfNewFile;

    public OpenFileStatement(Expression expression) {
        this.nameOfNewFile = expression;
    }

    @Override
    public String toString(){
        return "openFile(" + this.nameOfNewFile.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value val = this.nameOfNewFile.eval(symbolTable, state.getHeap());
        if (val.getType().equals(new StringType())){
            StringValue stringVal = (StringValue) val; // down casting
            if(fileTable.isDefined(stringVal))
                throw new StatementException("File is already opened");
            else {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(stringVal.getValue()));
                    fileTable.add(stringVal, reader);
                } catch (IOException exception) {
                    throw new FileException("File cannot be opened " + exception.getMessage());
                }
            }
        } else
            throw new StatementException("OpenReadFile expression is not a string");

        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new OpenFileStatement(this.nameOfNewFile);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type type = nameOfNewFile.typecheck(table);
        if (type.equals(new StringType())) {
            return table;
        } else {
            throw new TypeException("Expression not of type string");
        }
    }

}
