package typecheck.model.stmt;

import typecheck.model.PrgState;
import typecheck.model.adt.MyException;
import typecheck.model.adt.MyIDictionary;
import typecheck.model.adt.MyIStack;
import typecheck.model.type.Type;

public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt first, IStmt second ){
        this.first=first;
        this.second=second;
    }

    @Override
    public synchronized PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getMyStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));

    }

    @Override
    public String toString(){
        return "[" + first.toString() + ";" + second.toString() + "]";}
}
