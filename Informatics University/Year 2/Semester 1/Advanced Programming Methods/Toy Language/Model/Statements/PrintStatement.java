package Model.Statements;

import Controller.MyException;
import Model.Expressions.Expression;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IOutputList;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStatement implements IStatement{
    Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Print(" + expression.toString() + ")"; 
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        IOutputList<Value> outputList = programState.getOut();
        Value value = this.expression.evaluate(programState.getSymTable(), programState.getHeap());
        outputList.add(value);

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    
}
