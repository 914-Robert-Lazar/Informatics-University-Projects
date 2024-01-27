package typecheck.model.stmt;

import typecheck.model.PrgState;
import typecheck.model.adt.MyException;
import typecheck.model.adt.MyIDictionary;
import typecheck.model.type.Type;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;

    public VarDeclStmt(String v, Type type) {
        name= v;
        this.type=type;
    }

    @Override
    public PrgState execute(PrgState prg) throws MyException {
        prg.getMySymTable().put(name, type.defaultValue());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, type);
        //System.out.println(typeEnv.lookup(name).toString());
        return typeEnv;
    }

    @Override
    public String toString() {
        return type+ " " + name;
    }
}
