package typecheck.model.stmt;

import typecheck.model.PrgState;
import typecheck.model.adt.MyException;
import typecheck.model.adt.MyIDictionary;
import typecheck.model.type.Type;

public interface IStmt {

    PrgState execute(PrgState prg) throws MyException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
