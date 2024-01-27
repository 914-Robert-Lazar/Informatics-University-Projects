package typecheck.model.stmt;

import typecheck.model.PrgState;
import typecheck.model.adt.MyException;
import typecheck.model.adt.MyIDictionary;
import typecheck.model.exp.Expression;
import typecheck.model.type.RefType;
import typecheck.model.type.Type;
import typecheck.model.value.RefValue;
import typecheck.model.value.Value;


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
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        Type typeE= exp.typecheck(typeEnv);
        //System.out.println(exp.toString()+" type: "+typeE.toString()+"new RefType(typeE):"+ new RefType(typeE));
        Type typeV= typeEnv.lookup(name);
        //System.out.println(name+" type: "+typeV);
        if (typeV.equals(new RefType(typeE)))
            return typeEnv;
        else throw new MyException("[Stmt:"+this+" Error]: variable type: "+typeV+" expression type: "+typeE);//right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new("+ name+","+exp+")";
    }
}
