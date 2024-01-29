package com.example.toylanguage_intellij;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.example.toylanguage_intellij.Controller.Controller;
import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Commands.RunExampleCommand;
import com.example.toylanguage_intellij.Model.Expressions.ArithmeticExpression;
import com.example.toylanguage_intellij.Model.Expressions.LogicalExpression;
import com.example.toylanguage_intellij.Model.Expressions.ReadFromHeapExpression;
import com.example.toylanguage_intellij.Model.Expressions.RelationalExpression;
import com.example.toylanguage_intellij.Model.Expressions.ValueExpression;
import com.example.toylanguage_intellij.Model.Expressions.VariableExpression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.*;
import com.example.toylanguage_intellij.Model.Statements.*;
import com.example.toylanguage_intellij.Model.Types.BooleanType;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.ReferenceType;
import com.example.toylanguage_intellij.Model.Types.StringType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.BooleanValue;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.StringValue;
import com.example.toylanguage_intellij.Model.Values.Value;
import com.example.toylanguage_intellij.Repository.IRepository;
import com.example.toylanguage_intellij.Repository.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class SelectProgramController{

    @FXML
    private ListView<RunExampleCommand> programsListView;

    @FXML
    private Button runProgramButton;

    @FXML
    void runSelectedProgram(ActionEvent event) throws IOException {
        Stage mainWindowStage = new Stage();
        FXMLLoader selectFxmlLoader = new FXMLLoader(getClass().getResource("runProgram.fxml"));
        AnchorPane root = (AnchorPane) selectFxmlLoader.load();
        Scene scene = new Scene(root, 1050, 600);
        RunProgramController mainController = selectFxmlLoader.getController();

        mainController.setSelectedController((programsListView.getSelectionModel().getSelectedItem().getController()));
        mainWindowStage.setTitle("Program Running");
        mainWindowStage.setScene(scene);
        mainWindowStage.show();
    }

    private static ProgramState createProgramState(IStatement statement) {
        IExecutionStack<IStatement> stack = new ExecutionStack<IStatement>();
        IDictionary<String, Value> symbolTable = new SymbolTable<String, Value>();
        IOutputList<Value> outputList = new OutputList<Value>();
        IDictionary<StringValue, BufferedReader> fileTable = new FileTable<StringValue, BufferedReader>();
        IHeap<Value> heap = new Heap<Value>();
        IProcedureTable<Pair<List<String>, IStatement>> procedureTable = new ProcedureTable<>();

        return new ProgramState(stack, symbolTable, outputList, fileTable, heap, procedureTable, statement);
    }

    @FXML
    public void initialize() {
        IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("number1", new IntegerType()), 
                new CompoundStatement(new AssignmentStatement("number1", new ValueExpression(new IntegerValue(3))),
                new PrintStatement(new VariableExpression("number1"))));
        try {
                example1.typecheck(new SymbolTable<String, Type>());
                ProgramState programState1 = createProgramState(example1);
                IRepository repository1 = new Repository("log1.txt");
                Controller controller1 = new Controller(repository1);
                controller1.addProgramToRepository(programState1);
                programsListView.getItems().add(new RunExampleCommand("1", example1.toString(), controller1));
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example1.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example2 = new IfStatement(new LogicalExpression(new RelationalExpression(new ValueExpression(new IntegerValue(5)),
                new ValueExpression(new IntegerValue(3)), "<="), 
                new ValueExpression(new BooleanValue(true)), 1), example1, new CompoundStatement(new VariableDeclarationStatement("var1", new IntegerType()), 
                new CompoundStatement(new AssignmentStatement("var1", new ArithmeticExpression(new ValueExpression(new IntegerValue(8)), new ValueExpression(new IntegerValue(2)), 4)),
                new PrintStatement(new VariableExpression("var1")))));
        try {
                example2.typecheck(new SymbolTable<String, Type>());
                ProgramState programState2 = createProgramState(example2);
                IRepository repository2 = new Repository("log2.txt");
                Controller controller2 = new Controller(repository2);
                controller2.addProgramToRepository(programState2);
                programsListView.getItems().add(new RunExampleCommand("2", example2.toString(), controller2));
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example2.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example3 = new CompoundStatement(new IfStatement(new LogicalExpression(new ValueExpression(new BooleanValue(false)), 
                new ValueExpression(new BooleanValue(true)), 2), new CompoundStatement(new VariableDeclarationStatement("bool1", new IntegerType()),
                new CompoundStatement(new AssignmentStatement("bool1", new LogicalExpression(new ValueExpression(new BooleanValue(false)), new ValueExpression(new BooleanValue(true)), 2)),
                new PrintStatement(new VariableExpression("bool1")))), example2), example1);
        try {
                example3.typecheck(new SymbolTable<String, Type>());
                ProgramState programState3 = createProgramState(example3);
                IRepository repository3 = new Repository("log3.txt");
                Controller controller3 = new Controller(repository3);
                controller3.addProgramToRepository(programState3);
                programsListView.getItems().add(new RunExampleCommand("3", example3.toString(), controller3));
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example3.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()), 
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))), 
                new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")), 
                new CompoundStatement(new VariableDeclarationStatement("varc", new IntegerType()), 
                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), 
                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), 
                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), 
                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), 
                new CloseRFileStatement(new VariableExpression("varf"))))))))));
        try {
                example4.typecheck(new SymbolTable<String, Type>());
                ProgramState programState4 = createProgramState(example4);
                IRepository repository4 = new Repository("log4.txt");
                Controller controller4 = new Controller(repository4);
                controller4.addProgramToRepository(programState4);      
                programsListView.getItems().add(new RunExampleCommand("4", example4.toString(), controller4));  
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example4.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example5 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), 
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))), 
                new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), 
                new ValueExpression(new IntegerValue(0)), ">"), new CompoundStatement(
                        new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",
                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)), 
                        2)))), new PrintStatement(new VariableExpression("v")))));
        try {
                example5.typecheck(new SymbolTable<String, Type>());
                ProgramState programState5 = createProgramState(example5);
                IRepository repository5 = new Repository("log5.txt");
                Controller controller5 = new Controller(repository5);
                controller5.addProgramToRepository(programState5);  
                programsListView.getItems().add(new RunExampleCommand("5", example5.toString(), controller5));           
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example5.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example6 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), 
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))), 
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), 
                new CompoundStatement(new NewStatement("a", new VariableExpression("v")), 
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(30))), 
                new PrintStatement(new ReadFromHeapExpression(new ReadFromHeapExpression(new VariableExpression("a")))))))));
        try {
                example6.typecheck(new SymbolTable<String, Type>());
                ProgramState programState6 = createProgramState(example6);
                IRepository repository6 = new Repository("log6.txt");
                Controller controller6 = new Controller(repository6);
                controller6.addProgramToRepository(programState6);
                programsListView.getItems().add(new RunExampleCommand("6", example6.toString(), controller6));            
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example6.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), 
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))), 
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(30))),
                new CompoundStatement(new PrintStatement(new ReadFromHeapExpression(new VariableExpression("v"))), 
                new CompoundStatement(new WriteToHeapStatement("v", new ValueExpression(new IntegerValue(30))), 
                new PrintStatement(new ArithmeticExpression(new ReadFromHeapExpression(new VariableExpression("v")), 
                new ValueExpression(new IntegerValue(5)), 1)))))));
        try {
                example7.typecheck(new SymbolTable<String, Type>());
                ProgramState programState7 = createProgramState(example7);
                IRepository repository7 = new Repository("log7.txt");
                Controller controller7 = new Controller(repository7);
                controller7.addProgramToRepository(programState7);
                programsListView.getItems().add(new RunExampleCommand("7", example7.toString(), controller7));       
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example7.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example8 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), 
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())), 
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))), 
                new CompoundStatement(new NewStatement("a", new ValueExpression(new IntegerValue(22))), 
                new CompoundStatement(new ForkStatement(new CompoundStatement(new NewStatement("a", new ValueExpression(new IntegerValue(30))),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))), 
                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadFromHeapExpression(new VariableExpression("a"))))))), 
                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadFromHeapExpression(new VariableExpression("a")))))))));
        try {
                example8.typecheck(new SymbolTable<String, Type>());
                ProgramState programState8 = createProgramState(example8);
                IRepository repository8 = new Repository("log8.txt");
                Controller controller8 = new Controller(repository8);
                controller8.addProgramToRepository(programState8);
                programsListView.getItems().add(new RunExampleCommand("8", example8.toString(), controller8));    
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example8.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example9 = new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())), 
                new CompoundStatement(new VariableDeclarationStatement("counter", new IntegerType()), 
                new WhileStatement(new RelationalExpression(new VariableExpression("counter"), new ValueExpression(new IntegerValue(10)), "<"), 
                new CompoundStatement(new ForkStatement(new ForkStatement(new NewStatement("a", new VariableExpression("counter")))), 
                new AssignmentStatement("counter", new ArithmeticExpression(new VariableExpression("counter"), new ValueExpression(new IntegerValue(1)), 1))))));
        try {
                example9.typecheck(new SymbolTable<String, Type>());
                ProgramState programState9 = createProgramState(example9);
                IRepository repository9 = new Repository("log9.txt");
                Controller controller9 = new Controller(repository9);
                controller9.addProgramToRepository(programState9);
                programsListView.getItems().add(new RunExampleCommand("9", example9.toString(), controller9));
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example9.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStatement example10 = new CompoundStatement(
                new NewProcedureStatement(
                        "sum",
                        List.of("a", "b"),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v", new IntegerType()),
                                new CompoundStatement(
                                        new AssignmentStatement(
                                                "v",
                                                new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), 1)
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                ),
                new CompoundStatement(
                        new NewProcedureStatement(
                                "product",
                                List.of("a", "b"),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("v", new IntegerType()),
                                        new CompoundStatement(
                                                new AssignmentStatement(
                                                        "v",
                                                        new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), 3)
                                                ),
                                                new PrintStatement(new VariableExpression("v"))
                                        )
                                )
                        ),
                        new CompoundStatement(
                                new VariableDeclarationStatement(
                                        "v",
                                        new IntegerType()
                                ),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(
                                                "w",
                                                new IntegerType()
                                        ),
                                        new CompoundStatement(
                                                new AssignmentStatement(
                                                        "v",
                                                        new ValueExpression(new IntegerValue(2))
                                                ),
                                                new CompoundStatement(
                                                        new AssignmentStatement(
                                                                "w",
                                                                new ValueExpression(new IntegerValue(5))
                                                        ),
                                                        new CompoundStatement(
                                                                new CallProcedureStatement(
                                                                        "sum",
                                                                        List.of(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(10)), 3),
                                                                                new VariableExpression("w")
                                                                        )
                                                                ),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                        new ForkStatement(
                                                                                new CompoundStatement(
                                                                                        new CallProcedureStatement(
                                                                                                "product",
                                                                                                List.of(
                                                                                                        new VariableExpression("v"),
                                                                                                        new VariableExpression("w")
                                                                                                )
                                                                                        ),
                                                                                        new ForkStatement(
                                                                                                new CallProcedureStatement(
                                                                                                        "sum",
                                                                                                        List.of(new VariableExpression("v"), new VariableExpression("w"))
                                                                                                )
                                                                                        )
                                                                                )

                                                                        )
                                                                )
                                                        )

                                                )
                                        )
                                )
                        )
                )

        );
        try {
            example10.typecheck(new SymbolTable<String, Type>());
            ProgramState programState10 = createProgramState(example10);
            IRepository repository10 = new Repository("log10.txt");
            Controller controller10 = new Controller(repository10);
            controller10.addProgramToRepository(programState10);
            programsListView.getItems().add(new RunExampleCommand("10", example10.toString(), controller10));
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(example10.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
