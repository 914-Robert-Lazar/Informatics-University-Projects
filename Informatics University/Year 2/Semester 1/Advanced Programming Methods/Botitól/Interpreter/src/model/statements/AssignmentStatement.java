package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.adt.ListInterface;
import model.adt.StackInterface;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class AssignmentStatement implements Statement{
    private String id;
    private Expression expression;

    public AssignmentStatement(String _id,Expression _exp){
        id=_id;
        expression=_exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        StackInterface<Statement> statements=state.getExecutionStack();
        DictionaryInterface<String, Value> symbolTable=state.getSymbolsTable();
        HeapInterface<Integer,Value> heapTable=state.getHeapTable();
        ListInterface<Value> out=state.getOut();
        if (symbolTable.isDefined(id)){
            Value val=expression.eval(symbolTable,heapTable);
            Type type=symbolTable.lookup(id).getType();
            if (val.getType().equals(type)){
                symbolTable.update(id,val);
            }else{
                throw new InterpreterException("The declared type of the variable:" + id + " is not matching with the value");
            }
        }
        else{
            throw new InterpreterException("The variable: " + id + " is not defined");
        }
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new AssignmentStatement(this.id,this.expression.deepCopy()); //TODO:deepcopy
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type variableType=typeEnvironment.lookup(id);
        Type expressionType= expression.typeCheck(typeEnvironment);
        if (variableType.equals(expressionType))
            return typeEnvironment;
        else
            throw new InterpreterException("Assignment statement: right hand and left hand side have different types.");
    }

    @Override
    public String toString() {
        return id+"="+expression.toString()+";";
    }
}
