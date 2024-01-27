package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStatement implements Statement{
    private Expression expression;
    private Statement statement;
    public WhileStatement(Expression expression,Statement statement){
        this.statement=statement;
        this.expression=expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value value=expression.eval(state.getSymbolsTable(), state.getHeapTable());
        if (value.getType().equals(new BoolType())){
            BoolValue val=(BoolValue) value;
            if (val.getValue()){
                state.getExecutionStack().push(this);
                state.getExecutionStack().push(statement);
            }
        }else{
            throw new InterpreterException("Expression is not boolean");
        }
        return null;
    }

    @Override
    public String toString() {
        return "(while ("+ expression+") "+statement+");";
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new WhileStatement(expression.deepCopy(),statement.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type expressionType=expression.typeCheck(typeEnvironment);
        if (expressionType.equals(new BoolType())){
            statement.typeCheck(typeEnvironment.copy());
            return typeEnvironment;
        }
        else
            throw new InterpreterException("While statement: Expression is not of type boolean");
    }
}
