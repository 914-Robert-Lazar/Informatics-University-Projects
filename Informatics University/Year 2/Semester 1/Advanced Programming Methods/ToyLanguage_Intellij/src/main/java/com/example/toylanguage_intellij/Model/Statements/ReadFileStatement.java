package com.example.toylanguage_intellij.Model.Statements;

import java.io.BufferedReader;
import java.io.IOException;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.StringType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.StringValue;
import com.example.toylanguage_intellij.Model.Values.Value;

public class ReadFileStatement implements IStatement{
    Expression expression;
    String variableName;

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "ReadFile(" + expression.toString() + ", " + variableName + ")"; 
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        if (!symbolTable.isDefined(variableName)) {
            throw new MyException("The variable is not declared yet.");
        }
        else {
            Value value = symbolTable.findValue(variableName);
            if (!(value.getType().equals(new IntegerType()))) {
                throw new MyException("The value of variable is not an int.");
            }
            else { 
                Value fileNameValue = this.expression.evaluate(programState.getSymTable(), programState.getHeap());
                if (!(fileNameValue.getType() instanceof StringType)) {
                    throw new MyException("The expression does not evaluate to a string.");
                }
                else {
                    IDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
                    StringValue fileName = (StringValue)fileNameValue;
                    if (!fileTable.isDefined(fileName)) {
                        throw new MyException("The file is not open.");
                    }
                    else {
                        BufferedReader file = fileTable.findValue(fileName);
                        String line = file.readLine();
                        Value newValue;
                        if (line == null) {
                            newValue = new IntegerValue(0);
                        }
                        else {
                            newValue = new IntegerValue(Integer.parseInt(line));
                        }
                        symbolTable.put(variableName, newValue);
                    }
                }
            }
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVariable = typeEnv.findValue(variableName);
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeVariable.equals(new IntegerType())) {
            if (typeExpression.equals(new StringType())) {
                return typeEnv;
            }
            else {
                throw new MyException("The expression for the file name for ReadFile does not have a type string.");
            }
        }
        else {
            throw new MyException("The type of the variable for ReadFile does not have a type int.");
        }
    }
    
}
