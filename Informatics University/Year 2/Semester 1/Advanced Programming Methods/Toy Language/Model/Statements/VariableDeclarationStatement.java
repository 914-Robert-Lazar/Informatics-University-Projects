package Model.Statements;

import Controller.MyException;
import Model.ProgramStateComponents.ISymbolTable;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.BooleanType;
import Model.Types.IntegerType;
import Model.Types.Type;
import Model.Values.BooleanValue;
import Model.Values.IntegerValue;
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
        ISymbolTable<String, Value> symbolTable = programState.getSymTable();
        if (symbolTable.isDefined(id)) {
            throw new MyException("The variable already declared before.");
        }
        else {
            if (type instanceof IntegerType) {
                symbolTable.put(id, new IntegerValue(0));
            }
            else if (type instanceof BooleanType) {
                symbolTable.put(id, new BooleanValue(false));
            }
        }
        
        return programState;
    }

    @Override
    public String toString() {
        return type.toString() + " " + id;
    }
    
}
