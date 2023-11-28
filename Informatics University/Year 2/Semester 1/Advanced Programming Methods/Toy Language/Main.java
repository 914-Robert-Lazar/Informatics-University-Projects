import java.io.BufferedReader;
import java.io.IOException;
import Controller.Controller;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.LogicalExpression;
import Model.Expressions.ReadFromHeapExpression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramStateComponents.ExecutionStack;
import Model.ProgramStateComponents.FileTable;
import Model.ProgramStateComponents.Heap;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IExecutionStack;
import Model.ProgramStateComponents.IHeap;
import Model.ProgramStateComponents.IOutputList;
import Model.ProgramStateComponents.OutputList;
import Model.ProgramStateComponents.ProgramState;
import Model.ProgramStateComponents.SymbolTable;
import Model.Statements.AssignmentStatement;
import Model.Statements.CloseRFileStatement;
import Model.Statements.CompoundStatement;
import Model.Statements.IStatement;
import Model.Statements.IfStatement;
import Model.Statements.NewStatement;
import Model.Statements.OpenRFileStatement;
import Model.Statements.PrintStatement;
import Model.Statements.ReadFileStatement;
import Model.Statements.VariableDeclarationStatement;
import Model.Statements.WhileStatement;
import Model.Types.BooleanType;
import Model.Types.IntegerType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BooleanValue;
import Model.Values.IntegerValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.Repository;
import View.TextMenu;
import View.Commands.ExitCommand;
import View.Commands.RunExampleCommand;

public class Main {
    static ProgramState createProgramState(IStatement statement) {
        IExecutionStack<IStatement> stack = new ExecutionStack<IStatement>();
        IDictionary<String, Value> symbolTable = new SymbolTable<String, Value>();
        IOutputList<Value> outputList = new OutputList<Value>();
        IDictionary<StringValue, BufferedReader> fileTable = new FileTable<StringValue, BufferedReader>();
        IHeap<Value> heap = new Heap<Value>();
        ProgramState currentProgramState = new ProgramState(stack, symbolTable, outputList, fileTable, heap, statement);

        return currentProgramState;
    }
    
    public static void main(String[] args) throws IOException {
        //My User interface
        // try (Scanner scanner = new Scanner(System.in)) {
        //     System.out.print("The file path to the log file: ");
        //     String logFilePath = scanner.nextLine();
        //     IRepository repository = new Repository(logFilePath);
        //     Controller controller = new Controller(repository);
        //     View view = new View(controller);
        //     view.menu();
        // }

        //Given user interface
        IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("number1", new IntegerType()), 
                new CompoundStatement(new AssignmentStatement("number1", new ValueExpression(new IntegerValue(3))),
                new PrintStatement(new VariableExpression("number1"))));
        ProgramState programState1 = createProgramState(example1);
        IRepository repository1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repository1);
        controller1.addProgramToRepository(programState1);

        IStatement example2 = new IfStatement(new LogicalExpression(new RelationalExpression(new ValueExpression(new IntegerValue(5)),
                new ValueExpression(new IntegerValue(3)), "<="), 
                new ValueExpression(new BooleanValue(true)), 1), example1, new CompoundStatement(new VariableDeclarationStatement("var1", new IntegerType()), 
                new CompoundStatement(new AssignmentStatement("var1", new ArithmeticExpression(new ValueExpression(new IntegerValue(8)), new ValueExpression(new IntegerValue(2)), 4)),
                new PrintStatement(new VariableExpression("var1")))));
        ProgramState programState2 = createProgramState(example2);
        IRepository repository2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repository2);
        controller2.addProgramToRepository(programState2);

        IStatement example3 = new CompoundStatement(new IfStatement(new LogicalExpression(new ValueExpression(new BooleanValue(false)), 
                new ValueExpression(new BooleanValue(true)), 2), new CompoundStatement(new VariableDeclarationStatement("bool1", new BooleanType()), 
                new CompoundStatement(new AssignmentStatement("bool1", new LogicalExpression(new ValueExpression(new BooleanValue(false)), new ValueExpression(new BooleanValue(true)), 2)),
                new PrintStatement(new VariableExpression("bool1")))), example2), example1);
        ProgramState programState3 = createProgramState(example3);
        IRepository repository3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repository3);
        controller3.addProgramToRepository(programState3);

        IStatement example4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()), 
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))), 
                new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")), 
                new CompoundStatement(new VariableDeclarationStatement("varc", new IntegerType()), 
                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), 
                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), 
                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), 
                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), 
                new CloseRFileStatement(new VariableExpression("varf"))))))))));
        ProgramState programState4 = createProgramState(example4);
        IRepository repository4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repository4);
        controller4.addProgramToRepository(programState4);

        IStatement example5 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), 
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))), 
                new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), 
                new ValueExpression(new IntegerValue(0)), ">"), new CompoundStatement(
                        new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",
                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)), 
                        2)))), new PrintStatement(new VariableExpression("v")))));
        ProgramState programState5 = createProgramState(example5);
        IRepository repository5 = new Repository("log5.txt");
        Controller controller5 = new Controller(repository5);
        controller5.addProgramToRepository(programState5);

        IStatement example6 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), 
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))), 
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), 
                new CompoundStatement(new NewStatement("a", new VariableExpression("v")), 
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(30))), 
                new PrintStatement(new ReadFromHeapExpression(new ReadFromHeapExpression(new VariableExpression("a")))))))));
        ProgramState programState6 = createProgramState(example6);
        IRepository repository6 = new Repository("log6.txt");
        Controller controller6 = new Controller(repository6);
        controller6.addProgramToRepository(programState6);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", example1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", example2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", example4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", example5.toString(), controller5));  
        menu.addCommand(new RunExampleCommand("6", example6.toString(), controller6));
        menu.show();
    }
}
