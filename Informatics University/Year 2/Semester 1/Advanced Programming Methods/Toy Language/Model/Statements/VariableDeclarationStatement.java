package Model.Statements;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class VariableDeclarationStatement implements IStatement {
    String id;
    Type type;

    public VariableDeclarationStatement(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        if (symbolTable.isDefined(id)) {
            throw new MyException("The variable already declared before.");
        }
        else {
            symbolTable.put(id, type.defaultValue());
        }
        
        return null;
    }

    @Override
    public String toString() {
        return type.toString() + " " + id;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(id, type);
        return typeEnv;
    }
    
}
