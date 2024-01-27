package model.Statements;

import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.BoolType;
import model.Types.Type;

public class ConditionalAssignmentStatement implements StatementInterface{
    String id;
    private final Expression exp1;
    private final Expression exp2;
    private final Expression exp3;

    public ConditionalAssignmentStatement(String id, Expression exp1, Expression exp2, Expression exp3) {
        this.id = id;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();

        StatementInterface newStatement = new IfStatement(
                this.exp1,
                new AssignStatement(this.id, this.exp2),
                new AssignStatement(this.id, this.exp3));

        stack.push(newStatement);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ConditionalAssignmentStatement(this.id, this.exp1, this.exp2, this.exp3);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type exp1Type = this.exp1.typecheck(table);
        Type exp2Type = this.exp2.typecheck(table);
        Type exp3Type = this.exp3.typecheck(table);
        Type vType = table.lookup(this.id);

        if(!exp1Type.equals(new BoolType()))
            throw new TypeException("Ternary: first expression is not bool");
        if(exp2Type.equals(exp3Type) && exp2Type.equals(vType))
            return table;
        else
            throw new TypeException("Ternary: second, third expressions and v var do not match in types");
    }

    @Override
    public String toString(){
        return this.id + "=(" + this.exp1 + ")?" + this.exp2 + ":" + this.exp3;
    }
}
