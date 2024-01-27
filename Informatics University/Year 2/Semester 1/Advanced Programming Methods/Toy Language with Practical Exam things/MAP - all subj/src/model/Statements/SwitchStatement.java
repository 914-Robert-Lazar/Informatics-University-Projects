package model.Statements;

import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.Expressions.Expression;
import model.Expressions.RelationalExpression;
import model.ProgramState;
import model.Types.Type;

public class SwitchStatement implements StatementInterface{
    private final Expression exp;
    private final Expression exp1;
    private final Expression exp2;
    private final StatementInterface stmt1;
    private final StatementInterface stmt2;
    private final StatementInterface stmt3;

    public SwitchStatement(Expression exp, Expression exp1, Expression exp2, StatementInterface stmt1, StatementInterface stmt2, StatementInterface stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();

        StatementInterface newStatement = new IfStatement(
                new RelationalExpression("==", this.exp, this.exp1),
                this.stmt1,
                new IfStatement(
                        new RelationalExpression("==", this.exp, this.exp2),
                        this.stmt2,
                        this.stmt3
                )
        );

        stack.push(newStatement);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new SwitchStatement(this.exp, this.exp1, this.exp2, this.stmt1, this.stmt2, this.stmt3);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        this.stmt1.typecheck(table.deepCopy());
        this.stmt2.typecheck(table.deepCopy());
        this.stmt3.typecheck(table.deepCopy());
        Type expType = this.exp.typecheck(table);
        Type exp1Type = this.exp1.typecheck(table);
        Type exp2Type = this.exp2.typecheck(table);

        if(expType.equals(exp1Type) && expType.equals(exp2Type))
            return table;
        else throw new TypeException("Types don't match");
    }

    @Override
    public String toString() {
        return "switch(" + this.exp + ")" + "( case " + this.exp1.toString() + ": " + this.stmt1.toString() + ") ( case " + this.exp2.toString() + ": " + this.stmt2.toString() + ") default: " + this.stmt3.toString() + ")";
    }
}

