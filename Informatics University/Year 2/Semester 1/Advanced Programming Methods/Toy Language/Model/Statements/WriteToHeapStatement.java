package Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import Controller.MyException;
import Model.Expressions.Expression;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IHeap;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class WriteToHeapStatement implements IStatement{
    String variableName;
    Expression expression;

    public WriteToHeapStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        IHeap<Value> heap = programState.getHeap();

        if (!symbolTable.isDefined(variableName)) {
            throw new MyException("The variable is not defined.");
        }
        else {
            Value variable = symbolTable.findValue(variableName);
            if (!(variable.getType() instanceof ReferenceType)) {
                throw new MyException("The variable is not a reference variable.");
            }
            else if (!heap.isDefined(((ReferenceValue) variable).getAddress())) {
                throw new MyException("The heap address not yet allocated.");
            }
            else {
                Value newValue = expression.evaluate(symbolTable, heap);
                if (!newValue.getType().equals(((ReferenceValue) variable).getLocationType())) {
                    throw new MyException("The type of the expression and the location type of the variable are different.");
                }
                else {
                    heap.update(((ReferenceValue) variable).getAddress(), newValue);
                }
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "WriteToHeap(" + this.variableName + ", " + this.expression.toString() + ")";
    }
    
    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVariable = typeEnv.findValue(variableName);
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeVariable.equals(new ReferenceType(typeExpression))) {
            return typeEnv;
        }
        else {
            throw new MyException("The type of the expression and the location type of the varibale are different.");
        }
    }
}
