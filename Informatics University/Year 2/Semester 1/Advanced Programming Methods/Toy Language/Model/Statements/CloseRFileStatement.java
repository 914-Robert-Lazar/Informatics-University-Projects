package Model.Statements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import Controller.MyException;
import Model.Expressions.Expression;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.StringType;
import Model.Values.Value;
import Model.Values.StringValue;

public class CloseRFileStatement implements IStatement {
     Expression expression;

    public CloseRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "CloseRFile(" + expression.toString() + ")"; 
    }


    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        Value fileNameValue = this.expression.evaluate(programState.getSymTable(), programState.getHeap());
        if (!(fileNameValue.getType() instanceof StringType)) {
            throw new MyException("The expression does not evaluate to a string.");
        }
        else {
            IDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
            StringValue fileName = (StringValue)fileNameValue;
            if (!fileTable.isDefined(fileName)) {
                throw new MyException("The file is not open.");
            }
            else {
                BufferedReader file = fileTable.findValue(fileName);
                file.close();
                fileTable.remove(fileName);
            }
        }

        return programState;
    }
    
}
