package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyDictionary;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.BoolType;
import model.Types.Type;
import model.Values.BoolValue;
import model.Values.Value;

import java.util.Map;

public class IfStatement implements StatementInterface{
    private final Expression expression;
    private final StatementInterface thenStatement;
    private final StatementInterface elseStatement;

    public IfStatement(Expression expression, StatementInterface thenStatement, StatementInterface elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        Value condition = expression.eval(symbolTable, state.getHeap());
        if (!condition.getType().equals(new BoolType()))
            throw new StatementException("Conditional expression is not a boolean");
        else{
            if(condition.equals(new BoolValue(true)))
                stack.push(thenStatement);
            else
                stack.push(elseStatement);
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new IfStatement(this.expression, this.thenStatement, this.elseStatement);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type expressionType = expression.typecheck(table);
        if (expressionType.equals(new BoolType())) {
            thenStatement.typecheck(table.deepCopy());
            elseStatement.typecheck(table.deepCopy());
            return table;
        } else {
            throw new TypeException("Condition not of type bool");
        }
    }

    @Override
    public String toString(){
        return "(IF(" + expression.toString() + ") THEN(" + thenStatement.toString() + ") ELSE(" + elseStatement.toString() + "))";
    }
}
