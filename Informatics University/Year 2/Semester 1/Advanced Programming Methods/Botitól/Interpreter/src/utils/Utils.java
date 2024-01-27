package utils;

import model.statements.CompoundStatement;
import model.statements.NopStatement;
import model.statements.Statement;

public class Utils {
    public static Statement buildStatement(Statement... statements){
        if (statements.length==0){
            return new NopStatement();
        }
        if (statements.length==1){
            return statements[0];
        }
        Statement toReturn=new CompoundStatement(statements[0],statements[1]);
        for (int i=2;i<statements.length;++i){
            toReturn=new CompoundStatement(toReturn,statements[i]);
        }
        return toReturn;
    }
}
