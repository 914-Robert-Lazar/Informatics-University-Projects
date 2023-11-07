package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.ISymbolTable;
import Model.Values.Value;

public interface Expression {
    Value evaluate(ISymbolTable<String, Value> table) throws MyException;
}
