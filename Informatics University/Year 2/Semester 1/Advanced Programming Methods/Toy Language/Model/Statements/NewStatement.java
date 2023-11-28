package Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import Controller.MyException;
import Model.Expressions.Expression;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IHeap;
import Model.ProgramStateComponents.ProgramState;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class NewStatement implements IStatement {
    String name;
    Expression expression;

    public NewStatement(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    } 
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        IHeap<Value> heap = programState.getHeap();
        if (!symbolTable.isDefined(name)) {
            throw new MyException("The variable is not yet defined.");
        }
        else  {
            Value value = expression.evaluate(programState.getSymTable(), programState.getHeap());
            if (!value.getType().equals(((ReferenceValue) symbolTable.findValue(name)).getLocationType())) {
                throw new MyException("The type of the expression's result and the location type of the varialbe are different.");
            }
            else {
                int currentAddress = heap.put(value);
                symbolTable.put(name, new ReferenceValue(currentAddress, value.getType()));
            }
        }
        return programState;
    }
    @Override
    public String toString() {
        return "new(" + this.name + ", " + this.expression.toString() + ")";
    }
}
