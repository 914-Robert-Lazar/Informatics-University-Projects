package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.types.*;
import model.values.StringValue;
import model.values.Value;

public class VariableDeclarationStatement implements Statement{
    private String name;
    private Type type;

    public VariableDeclarationStatement(String _name,Type _type){
        name=_name;
        type=_type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        DictionaryInterface<String, Value> symbolsTable=state.getSymbolsTable();
        if (symbolsTable.isDefined(name))
            throw new InterpreterException("Variable is already defined");
        else {
            if (type instanceof IntType)
                symbolsTable.update(name,new IntType().defaultValue());
            else if (type instanceof BoolType){
                symbolsTable.update(name,new BoolType().defaultValue());
            }else if(type instanceof StringType){
                symbolsTable.update(name,new StringType().defaultValue());
            } else if (type instanceof ReferenceType) {
                symbolsTable.update(name,new ReferenceType(((ReferenceType) type).getInner()).defaultValue());
            }
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        if (type.equals(new IntType()))
            return new VariableDeclarationStatement(this.name,new IntType());
        else if (type.equals(new BoolType())) {
            return new VariableDeclarationStatement(this.name,new BoolType());
        }
        else if (type.equals(new StringType())){
            return new VariableDeclarationStatement(this.name,new StringType());
        }
        else if(type instanceof ReferenceType){
            return new VariableDeclarationStatement(name,new ReferenceType(((ReferenceType) type).getInner()));
        }
        return null;


    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        typeEnvironment.update(name,type);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return type + " " + name+";";
    }
}
