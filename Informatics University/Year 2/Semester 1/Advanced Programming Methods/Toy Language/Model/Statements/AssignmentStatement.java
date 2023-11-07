package Model.Statements;

import Controller.MyException;
import Model.Expressions.Expression;
import Model.ProgramStateComponents.ISymbolTable;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignmentStatement implements IStatement{
    String id;
    Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ISymbolTable<String, Value> symbolTable = programState.getSymTable();

        if (symbolTable.isDefined(id)) {
            Value rightSide = this.expression.evaluate(symbolTable);

            Type idType = (symbolTable.findValue(id)).getType();
            if (idType.equals(rightSide.getType())) {
                symbolTable.put(id, rightSide);
            }
            else {
                throw new MyException("The declared type for variable " + id + "and the type of assigned expression do not match.");
            }
        }
        else {
            throw new MyException("The used variable " + id + " was not declared yet.");
        }

        return programState;
    }

    @Override
    public String toString() {
        return id + " = " + expression.toString();
    }
    
}
