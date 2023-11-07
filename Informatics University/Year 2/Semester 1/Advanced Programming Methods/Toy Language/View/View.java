package View;

import java.util.InputMismatchException;
import java.util.Scanner;

import Controller.Controller;
import Controller.MyException;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.LogicalExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramStateComponents.ExecutionStack;
import Model.ProgramStateComponents.IExecutionStack;
import Model.ProgramStateComponents.IOutputList;
import Model.ProgramStateComponents.ISymbolTable;
import Model.ProgramStateComponents.OutputList;
import Model.ProgramStateComponents.ProgramState;
import Model.ProgramStateComponents.SymbolTable;
import Model.Statements.AssignmentStatement;
import Model.Statements.CompoundStatement;
import Model.Statements.IStatement;
import Model.Statements.IfStatement;
import Model.Statements.PrintStatement;
import Model.Statements.VariableDeclarationStatement;
import Model.Types.BooleanType;
import Model.Types.IntegerType;
import Model.Values.BooleanValue;
import Model.Values.IntegerValue;
import Model.Values.Value;


public class View {
    Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }
    IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("number1", new IntegerType()), 
                new CompoundStatement(new AssignmentStatement("number1", new ValueExpression(new IntegerValue(3))),
                new PrintStatement(new VariableExpression("number1"))));

    IStatement example2 = new IfStatement(new LogicalExpression(new ValueExpression(new BooleanValue(false)), 
                new ValueExpression(new BooleanValue(true)), 1), example1, new CompoundStatement(new VariableDeclarationStatement("var1", new IntegerType()), 
                new CompoundStatement(new AssignmentStatement("var1", new ArithmeticExpression(new ValueExpression(new IntegerValue(8)), new ValueExpression(new IntegerValue(2)), 4)),
                new PrintStatement(new VariableExpression("var1")))));

    IStatement example3 = new CompoundStatement(new IfStatement(new LogicalExpression(new ValueExpression(new BooleanValue(false)), 
                new ValueExpression(new BooleanValue(true)), 2), new CompoundStatement(new VariableDeclarationStatement("bool1", new BooleanType()), 
                new CompoundStatement(new AssignmentStatement("bool1", new LogicalExpression(new ValueExpression(new BooleanValue(false)), new ValueExpression(new BooleanValue(true)), 2)),
                new PrintStatement(new VariableExpression("bool1")))), example2), example1);
                
    public void menu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("0. Exit");
                System.out.println("1. Run all at once");
                System.out.println("2. Run step by step");
                try {
                    int option = scanner.nextInt();
                    switch (option) {
                        case 0:
                            return;
                        case 1:
                            this.runAtOnce(scanner);
                            break;
                        case 2:
                            this.runStepByStep(scanner);
                            break;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                }
            }

        }
    }

    private int printExampleMenu(Scanner scanner) {
        System.out.println("1. Run example1");
        System.out.println("2. Run example2");
        System.out.println("3. Run example3");

        return scanner.nextInt();
    }

    private void runAtOnce(Scanner scanner) {
        int option = this.printExampleMenu(scanner);
        switch (option) {
            case 1:
                this.runExample(example1);
                break;
            case 2:
                this.runExample(example2);
                break;
            case 3:
                this.runExample(example3);
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }

    private void runStepByStep(Scanner scanner) {
        int option = this.printExampleMenu(scanner);
        scanner.nextLine();
        switch (option) {
            case 1:
                this.runExampleStepByStep(example1);
                break;
            case 2:
                this.runExampleStepByStep(example2);
                break;
            case 3:
                this.runExampleStepByStep(example3);
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }

    private void runExample(IStatement example) {
        IExecutionStack<IStatement> stack = new ExecutionStack<IStatement>();
        ISymbolTable<String, Value> symbolTable = new SymbolTable<String, Value>();
        IOutputList<Value> outputList = new OutputList<Value>();
        ProgramState currentProgramState = new ProgramState(stack, symbolTable, outputList, example);
        this.controller.addProgramToRepository(currentProgramState);
        try {
            controller.allStep();
        }
        catch (MyException e) {
            e.printMessage();
        }
    }

    private void runExampleStepByStep(IStatement example) {
        IExecutionStack<IStatement> stack = new ExecutionStack<IStatement>();
        ISymbolTable<String, Value> symbolTable = new SymbolTable<String, Value>();
        IOutputList<Value> outputList = new OutputList<Value>();
        ProgramState currentProgramState = new ProgramState(stack, symbolTable, outputList, example);
        this.controller.addProgramToRepository(currentProgramState);
        try {
            controller.allStepByStep();
        }
        catch (MyException e) {
            e.printMessage();
        }
    }

}
