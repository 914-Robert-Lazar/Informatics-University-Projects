package textmenu.model.stmt.basic;

import textmenu.model.PrgState;
import textmenu.model.adt.MyException;
import textmenu.model.adt.MyIStack;

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
    public String toString(){
        return "[" + first.toString() + ";" + second.toString() + "]";}
}

