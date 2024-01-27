package com.example.toylanguage_intellij.Model.Statements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.StringType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.Value;
import com.example.toylanguage_intellij.Model.Values.StringValue;

public class CloseRFileStatement implements IStatement {
     Expression expression;

    public CloseRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "CloseRFile(" + expression.toString() + ")"; 
    }


    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
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
                file.close();
                fileTable.remove(fileName);
            }
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeExpression.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new MyException("The argument of CloseRFile does not have type string.");
        }
    }
    
}
