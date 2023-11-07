package Model.ProgramStateComponents;

import Model.Statements.IStatement;
import Model.Values.Value;

public class ProgramState {
    IExecutionStack<IStatement> executionStack;
    ISymbolTable<String, Value> symbolTable;
    IOutputList<Value> outputList;

    public ProgramState(IExecutionStack<IStatement> executionStack, ISymbolTable<String, Value> symbolTable, 
                        IOutputList<Value> outputList, IStatement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;

        this.executionStack.push(program);
    }

    public IExecutionStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public void setExecutionStack(IExecutionStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public ISymbolTable<String, Value> getSymTable() {
        return this.symbolTable;
    }

    public void setSymTable(ISymbolTable<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IOutputList<Value> getOut() {
        return this.outputList;
    }

    public void setOut(IOutputList<Value> outputList) {
        this.outputList = outputList;
    }

    @Override
    public String toString() {
        return "ProgramState { exeStack = " + this.executionStack.getReversed() +
                ", symTable = " + this.symbolTable +
                ", out = " + this.outputList +
                '}';
    }
}
