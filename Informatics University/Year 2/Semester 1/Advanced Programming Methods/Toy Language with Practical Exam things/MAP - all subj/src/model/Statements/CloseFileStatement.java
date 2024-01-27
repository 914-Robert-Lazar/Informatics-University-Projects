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
import java.io.IOException;

public class CloseFileStatement implements StatementInterface{
    private final Expression expFileName;

    public CloseFileStatement(Expression expression) {
        this.expFileName = expression;
    }

    @Override
    public String toString(){
        return "closeFile(" + this.expFileName.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value val = expFileName.eval(symbolTable, state.getHeap());
        if (val.getType().equals(new StringType())){
            StringValue stringFileName = (StringValue) val; // down casting
            if(!fileTable.isDefined(stringFileName))
                throw new StatementException("File does not exist");
            else {
                try {
                    BufferedReader reader = fileTable.lookup(stringFileName);
                    reader.close();
                    fileTable.remove(stringFileName);
                } catch (IOException exception) {
                    throw new FileException("File cannot be closed: " + exception.getMessage());
                }
            }
        } else
            throw new StatementException("CloseReadFile expression is not a string");

        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new CloseFileStatement(this.expFileName);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type type = expFileName.typecheck(table);
        if (type.equals(new StringType())) {
            return table;
        } else {
            throw new TypeException("Expression not of type string");
        }
    }
}
