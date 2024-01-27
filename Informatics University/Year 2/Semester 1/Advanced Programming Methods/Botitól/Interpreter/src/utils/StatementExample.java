package utils;

import exception.InterpreterException;
import model.expressions.*;
import model.statements.*;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;

import java.util.List;
import java.util.Vector;

public class StatementExample {
    public static List<Statement> generateStatements() throws InterpreterException {
        Vector<Statement> statements = new Vector<>();
        Statement ex1= Utils.buildStatement(new VariableDeclarationStatement("v",new IntType()),new AssignmentStatement("v",new ValueExpression(new IntValue(2))),new PrintStatement(new VariableExpression("v")));
        Statement ex2=Utils.buildStatement(new VariableDeclarationStatement("varf",new StringType()),new AssignmentStatement("varf", new ValueExpression(new StringValue("src/test.in"))),new OpenReadFileStatement(new VariableExpression("varf")),new VariableDeclarationStatement("varc",new IntType()),new ReadFileStatement("varc",new VariableExpression("varf")),new PrintStatement(new VariableExpression("varc")),new ReadFileStatement("varc",new VariableExpression("varf")),new PrintStatement(new VariableExpression("varc")),new CloseReadFileStatement(new VariableExpression("varf")));
        Statement ex3=Utils.buildStatement(new VariableDeclarationStatement("a",new IntType()),new VariableDeclarationStatement("b",new IntType()),new AssignmentStatement("a",new ValueExpression(new IntValue(2))),new AssignmentStatement("b",new ValueExpression(new IntValue(4))),new VariableDeclarationStatement("v",new ReferenceType(new IntType())),new New("v",new ArithmeticExpression(new VariableExpression("a"),new VariableExpression("b"), ArithmeticExpression.Operand.ADD)),new VariableDeclarationStatement("c",new ReferenceType(new ReferenceType(new IntType()))),new New("c",new VariableExpression("v")),new PrintStatement(new VariableExpression("v")),new PrintStatement(new VariableExpression("c")),new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("c")))));
        Statement ex4=Utils.buildStatement(new VariableDeclarationStatement("a",new IntType()),new VariableDeclarationStatement("b",new IntType()),new AssignmentStatement("a",new ValueExpression(new IntValue(2))),new AssignmentStatement("b",new ValueExpression(new IntValue(4))),new VariableDeclarationStatement("v",new ReferenceType(new IntType())),new New("v",new VariableExpression("a")),new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),new HeapWritingStatement("v",new VariableExpression("b")),new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))));
        Statement ex5=Utils.buildStatement(new VariableDeclarationStatement("v",new IntType()),new AssignmentStatement("v",new ValueExpression(new IntValue(4))),new WhileStatement(new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(1)), RelationalExpression.Operand.GREATER),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignmentStatement("v",new ArithmeticExpression(new VariableExpression("v"),new ValueExpression(new IntValue(1)), ArithmeticExpression.Operand.SUB)))),new PrintStatement(new VariableExpression("v")));
        Statement ex6=Utils.buildStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),new New("v",new ValueExpression(new IntValue(2))),new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),new New("a",new VariableExpression("v")),new New("v",new ValueExpression(new IntValue(30))),new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))));
        Statement ex7=Utils.buildStatement(new VariableDeclarationStatement("v",new IntType()),new VariableDeclarationStatement("a",new ReferenceType(new IntType())),new AssignmentStatement("v",new ValueExpression(new IntValue(10))),new New("a",new ValueExpression(new IntValue(22))),new ForkStatement(Utils.buildStatement(new HeapWritingStatement("a",new ValueExpression(new IntValue(30))),new AssignmentStatement("v",new ValueExpression(new IntValue(32))),new PrintStatement(new VariableExpression("v")),new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))),new PrintStatement(new VariableExpression("v")),new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))));
        Statement ex8=Utils.buildStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),new New("a",new ValueExpression(new IntValue(3))),new New("a",new ValueExpression(new IntValue(4))),new NopStatement());
        statements.add(ex1);
        statements.add(ex2);
        statements.add(ex3);
        statements.add(ex4);
        statements.add(ex5);
        statements.add(ex6);
        statements.add(ex7);
        statements.add(ex8);
        return statements;
    }
}
