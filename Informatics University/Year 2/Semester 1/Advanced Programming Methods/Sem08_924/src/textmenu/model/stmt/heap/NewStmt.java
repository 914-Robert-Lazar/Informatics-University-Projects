package textmenu.model.stmt.heap;

import textmenu.model.PrgState;
import textmenu.model.adt.MyException;
import textmenu.model.exp.Expression;
import textmenu.model.stmt.basic.IStmt;
import textmenu.model.value.RefValue;
import textmenu.model.value.Value;


public class NewStmt implements IStmt {
    String name;
    Expression exp;

    public NewStmt(String v, Expression exp) {
        name= v;
        this.exp=exp;
    }

    @Override
    public PrgState execute(PrgState prg) throws MyException {
        Value value= exp.evaluate(prg.getMySymTable(), prg.getMyHeapTable());
        int address= prg.getMyHeapTable().put(value);
        //RefValue ref= (RefValue) prg.getMySymTable().lookup(name);
        prg.getMySymTable().put(name, new RefValue(address, value.getType()));
        return prg;
    }

    @Override
    public String toString() {
        return "new("+ name+","+exp+")";
    }
}
