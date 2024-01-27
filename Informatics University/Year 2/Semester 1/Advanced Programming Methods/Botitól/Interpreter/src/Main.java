import controller.Controller;
import exception.InterpreterException;
import model.ProgramState;
import model.adt.Dictionary;
import model.adt.List;
import model.adt.Stack;
import model.expressions.ArithmeticExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;
import repository.Repository;
import view.View;

import java.io.IOException;
import java.util.random.RandomGenerator;

public class Main {
    public static void main(String[] args) throws InterpreterException {
//        Statement ex1=new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v",new BoolType()),new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new BoolValue(true))),new PrintStatement(new VariableExpression("v")))),new NopStatement() );
//        Stack<Statement> executionStack=new Stack<>();
//        Dictionary<String,Value> symbolsTable=new Dictionary<>();
//        List<Value> out=new List<>();
//        ProgramState state=new ProgramState(executionStack,symbolsTable,out,ex1);
//        Statement ex2=new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
//                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
//                        new CompoundStatement(new AssignmentStatement("a",
//                                new ArithmeticExpression(new ValueExpression(new IntValue(2)),new ArithmeticExpression(new ValueExpression(new IntValue(3)),new ValueExpression(new IntValue(5)), ArithmeticExpression.Operand.MUL), ArithmeticExpression.Operand.ADD)),
//                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression(new VariableExpression("a"),new ValueExpression(new IntValue(1)), ArithmeticExpression.Operand.ADD)),new PrintStatement(new VariableExpression("b"))))));
//        ProgramState state2=new ProgramState(new Stack<Statement>(),new Dictionary<String,Value>(),new List<Value>(),ex2);
//
//        Statement ex3=new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
//                new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
//                        new CompoundStatement(new AssignmentStatement("a",new ValueExpression(new BoolValue(true))),
//                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))),new AssignmentStatement("v",new ValueExpression(new IntValue(3)))),new PrintStatement(new VariableExpression("v"))))));
//        ProgramState state3=new ProgramState(new Stack<Statement>(),new Dictionary<String,Value>(),new List<Value>(),ex3);
//        Repository<ProgramState> repository=new Repository<>("src/log.txt");
//        repository.add(state);
//        repository.add(state2);
//        repository.add(state3);
//        Controller controller=new Controller(repository);
////        controller.setDisplayFlag(false);
//        View view=new View(controller);
//
//        try {
//            view.runConsole();
//        }catch (InterpreterException | IOException ie){
//            System.out.println(ie.toString());
//        }
    System.out.println(RandomGenerator.getDefault().nextInt(10,100));
    }
}