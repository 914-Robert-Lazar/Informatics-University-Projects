package Model.Statements;

import Model.Expressions.Expression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

public class OpenRFileStatement implements IStatement {
    Expression expression;

    public OpenRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "OpenRFile(" + expression.toString() + ")"; 
    }


    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException {
        Value value = this.expression.evaluate(programState.getSymTable(), programState.getHeap());
        if (!(value.getType() instanceof StringType)) {
            throw new MyException("The expression does not evaluate to a string.");
        }
        else {
            IDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
            StringValue fileName = (StringValue)value;
            if (fileTable.isDefined(fileName)) {
                throw new MyException("The file is already opened.");
            }
            else {
                BufferedReader file = new BufferedReader(new FileReader(fileName.getValue()));
                fileTable.put(fileName, file);
            }
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeExpression.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new MyException("The argument of OpenRFile does not have type string.");
        }
    }
}
