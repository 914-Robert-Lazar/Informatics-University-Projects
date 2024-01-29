package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IProcedureTable;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.SymbolTable;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.Value;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CallProcedureStatement implements IStatement {
    String procedureName;
    List<Expression> parameterValues;

    public CallProcedureStatement(String procedureName, List<Expression> parameterValues) {
        this.procedureName = procedureName;
        this.parameterValues = parameterValues;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IProcedureTable<Pair<List<String>, IStatement>> procedureTable = programState.getProcedureTable();
        if (procedureTable.exists(procedureName)) {
            Pair<List<String>, IStatement> pair = procedureTable.get(procedureName);
            if (parameterValues.size() == pair.getKey().size()) {
                IDictionary<String, Value> procedureSymbolTable = new SymbolTable<>();
                for (int i = 0; i < parameterValues.size(); i++) {
                    Value currentValue = parameterValues.get(i).evaluate(programState.getSymTable(), programState.getHeap());
                    procedureSymbolTable.put(pair.getKey().get(i), currentValue);
                }
                programState.getSymbolTableStack().push(procedureSymbolTable);
                programState.setSymTable(procedureSymbolTable);
                programState.getExecutionStack().push(new ReturnStatement());
                programState.getExecutionStack().push(pair.getValue());
            }
            else {
                throw new MyException("Invalid number of parameters");
            }
        }
        else {
            throw new MyException("Procedure does not exist");
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "call " + procedureName + " (" + parameterValues.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
    }
}
