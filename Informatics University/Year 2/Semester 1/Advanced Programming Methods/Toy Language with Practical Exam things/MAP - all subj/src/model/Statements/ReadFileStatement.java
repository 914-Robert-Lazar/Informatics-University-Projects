package model.Statements;

import exceptions.FileException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.IntType;
import model.Types.StringType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements StatementInterface{
    private final Expression expFileName; //varf
    private final String varLine; // varc

    public ReadFileStatement(Expression expression, String varName) {
        this.expFileName = expression;
        this.varLine = varName;
    }

    @Override
    public String toString(){
        return "readFile(" + this.expFileName.toString() + ", " + this.varLine + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();

        if(!symbolTable.isDefined(this.varLine))
            throw new FileException("The variable where you save the content of the line, " + this.varLine + " is not defined in the symbol table");
        Value valOfLine = symbolTable.lookup(this.varLine);
        if (!valOfLine.getType().equals(new IntType()))
            throw new StatementException("You can't save an integer value in the variable " + this.varLine);

        Value valFileName = expFileName.eval(symbolTable, state.getHeap());
        if (!valFileName.getType().equals(new StringType()))
            throw new StatementException("You can't have in the variable " + this.expFileName + " a file name because its value type is not a string");

        StringValue valFileNameString = (StringValue) valFileName;
        if (fileTable.isDefined(valFileNameString)) {
            try {
                BufferedReader reader = fileTable.lookup(valFileNameString);
                String line = reader.readLine();
                IntValue intVal;
                if (line == null)
                    intVal = new IntValue(0);
                else
                    intVal = new IntValue(Integer.parseInt(line));
                symbolTable.update(this.varLine, intVal);
                state.setSymbolTable(symbolTable);
            } catch (IOException exception) {
                throw new FileException("Cannot read from file");
            }
        } else{
            throw new StatementException("The string " + valFileNameString + " is not defined in file table");
        }

        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ReadFileStatement(this.expFileName, this.varLine);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type expressionType = expFileName.typecheck(table);
        Type variableType = table.lookup(varLine);
        if (expressionType.equals(new StringType())) {
            if (variableType.equals(new IntType())) {
                return table;
            } else {
                throw new TypeException("Variable not of type int");
            }
        } else {
            throw new TypeException("Expression not of type string");
        }
    }
}
