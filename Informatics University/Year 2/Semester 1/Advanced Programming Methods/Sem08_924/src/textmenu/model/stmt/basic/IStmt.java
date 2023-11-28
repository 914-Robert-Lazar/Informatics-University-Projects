package textmenu.model.stmt.basic;

import textmenu.model.PrgState;
import textmenu.model.adt.MyException;

public interface IStmt {

    PrgState execute(PrgState prg) throws MyException;
}

