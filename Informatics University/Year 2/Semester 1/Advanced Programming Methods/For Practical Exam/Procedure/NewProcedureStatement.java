package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.Type;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class NewProcedureStatement implements IStatement{
    String procedureName;
    List<String> parameterNames;
    IStatement bodyStatement;

    public NewProcedureStatement(String procedureName, List<String> parameterNames, IStatement bodyStatement) {
        this.procedureName = procedureName;
        this.parameterNames = parameterNames;
        this.bodyStatement = bodyStatement;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        if (programState.getProcedureTable().exists(procedureName)) {
            throw new MyException("The procedure already exists.");
        }

        programState.getProcedureTable().put(procedureName, new Pair<>(parameterNames, bodyStatement));

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "procedure " + procedureName + " (" + parameterNames.stream().map(String::valueOf).collect(Collectors.joining(", ")) + ") {" + bodyStatement.toString() + "} ";
    }
}
