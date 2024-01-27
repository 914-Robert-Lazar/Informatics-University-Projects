package model.Statements;

import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.Expressions.Expression;
import model.Expressions.RelationalExpression;
import model.Expressions.ValueExpression;
import model.Expressions.VariableExpression;
import model.ProgramState;
import model.Types.IntType;
import model.Types.Type;

public class ForStatement implements StatementInterface{
    private final Expression expression1; // v = exp1
    private final Expression expression2; // v < exp2
    private final Expression expression3; // v = exp3
    private final StatementInterface statement; // for(..) stmt

    public ForStatement(Expression expression1, Expression expression2, Expression expression3, StatementInterface statement) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();

        StatementInterface newStatement = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", this.expression1),
                        new WhileStatement(
                                new RelationalExpression("<", new VariableExpression("v"), this.expression2),
                                new CompoundStatement(statement, new AssignStatement("v", this.expression3))
                        )
                )
        );

        stack.push(newStatement);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ForStatement(this.expression1, this.expression2, this.expression3, this.statement);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        MyDictionaryInterface<String, Type> table1 = new VariableDeclarationStatement("v", new IntType()).typecheck(table.deepCopy());
        Type vType = table1.lookup("v");
        Type expression1Type = this.expression1.typecheck(table1);
        Type expression2Type = this.expression1.typecheck(table1);
        Type expression3Type = this.expression1.typecheck(table1);

        if(expression1Type.equals(new IntType())){
            if(expression2Type.equals(new IntType())){
                if(expression3Type.equals(new IntType())){
                    if(vType.equals(new IntType())){
                        return table;
                    } else throw new TypeException("v from for statement is not int");
                } else throw new TypeException("Expression3 from for statement is not int");
            } else throw new TypeException("Expression2 from for statement is not int");
        } else throw new TypeException("Expression1 from for statement is not int");
    }

    @Override
    public String toString() {
        return "for(v=" + expression1.toString() +
                ";v<" + expression2.toString() +
                ";v=" + expression3.toString() +
                ") {" + statement.toString() + " }";
    }
}
