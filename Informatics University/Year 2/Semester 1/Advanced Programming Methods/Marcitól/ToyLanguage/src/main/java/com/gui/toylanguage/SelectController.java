package com.gui.toylanguage;

import com.gui.toylanguage.adt.*;
import com.gui.toylanguage.controller.Controller;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.commands.RunCommand;
import com.gui.toylanguage.model.expressions.*;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.statements.*;
import com.gui.toylanguage.model.types.IntType;
import com.gui.toylanguage.model.types.RefType;
import com.gui.toylanguage.model.types.StringType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.BoolValue;
import com.gui.toylanguage.model.values.IntValue;
import com.gui.toylanguage.model.values.StringValue;
import com.gui.toylanguage.model.values.Value;
import com.gui.toylanguage.repository.IRepository;
import com.gui.toylanguage.repository.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;


public class SelectController {

    @FXML
    private ListView<RunCommand> programsListView;

    @FXML
    private Button runProgramButton;

    //creating a new window with the selected program
    @FXML
    void runSelectedProgram(ActionEvent event) throws IOException {
        Stage mainWindowStage = new Stage();
        FXMLLoader selectFxmlLoader = new FXMLLoader(getClass().getResource("runProgram.fxml"));
        AnchorPane root = (AnchorPane) selectFxmlLoader.load();
        Scene scene = new Scene(root, 840, 600);
        RunProgramController mainController = selectFxmlLoader.getController();
        //this is the RunCommand's controller not the gui's!
        //we return the currently selected runCommands controller so ew can Use its onestep method
        mainController.setSelectedController((programsListView.getSelectionModel().getSelectedItem().getController()));
        mainWindowStage.setTitle("Program Running");
        mainWindowStage.setScene(scene);
        mainWindowStage.show();
    }

    @FXML
    public void initialize(){


        IStmt ex1=new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new NOPStmt(), new CompStmt(new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))), new PrintStmt(new RelExp(new VarExp("a"), RelOps.SMALLER, new VarExp("b"))))))));
        try {
            MyIStack<IStmt> stk = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl =
                    new MyDictionary<String, Value>();
            MyIList<Value> out = new MyList<Value>();
            MyHeapDictionary hpTbl = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl = new MyDictionary<String, BufferedReader>();

            MyIDictionary<String, Type> typeEnv1 = new MyDictionary<String, Type>();
            ex1.typecheck(typeEnv1);
            PrgState prg1 = new PrgState(stk, symtbl, out, flTbl, hpTbl, ex1);
            IRepository repo1 = new Repository("log1.txt");
            repo1.add(prg1);
            Controller ctr1 = new Controller(repo1);

            programsListView.getItems().add(new RunCommand("1",ex1.toString(),ctr1));
        }catch (MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex1.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStmt ex2=new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+",new ValueExp(new IntValue(2)),new
                                ArithExp("*",new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp("+",new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        try {
            MyIStack<IStmt> stk2 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl2 =
                    new MyDictionary<String, Value>();
            MyIList<Value> out2 = new MyList<Value>();
            MyHeapDictionary hpTbl2 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl2 = new MyDictionary<String, BufferedReader>();

            MyIDictionary<String, Type> typeEnv2 = new MyDictionary<String, Type>();
            ex2.typecheck(typeEnv2);
            PrgState prg2 = new PrgState(stk2, symtbl2, out2, flTbl2, hpTbl2, ex2);
            IRepository repo2 = new Repository("log2.txt");
            repo2.add(prg2);
            Controller ctr2 = new Controller(repo2);

            programsListView.getItems().add(new RunCommand("2",ex2.toString(),ctr2));
        }catch (MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex2.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        IStmt ex4= new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenFileStmt(new VarExp("varf")),
                new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                        new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseFileStmt(new VarExp("varf"))))))))));
        try {
            MyIStack<IStmt> stk4 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl4 =
                    new MyDictionary<String, Value>();
            MyIList<Value> out4 = new MyList<Value>();
            MyHeapDictionary hpTbl4 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl4 = new MyDictionary<String, BufferedReader>();

            MyIDictionary<String, Type> typeEnv4 = new MyDictionary<String, Type>();
            ex4.typecheck(typeEnv4);
            PrgState prg4 = new PrgState(stk4, symtbl4, out4, flTbl4, hpTbl4, ex4);
            IRepository repo4 = new Repository("log4.txt");
            repo4.add(prg4);
            Controller ctr4 = new Controller(repo4);

           programsListView.getItems().add(new RunCommand("4",ex4.toString(),ctr4));
        }catch (MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex4.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStmt ex5 = new CompStmt(new CompStmt( new VarDeclStmt("r", new RefType((new IntType()))), new NewHeapStmt("r", new ValueExp(new IntValue(5)))),
                new CompStmt(new VarDeclStmt("r2", new RefType(new RefType( new IntType()))), new NewHeapStmt("r2", new VarExp("r"))));
        try {
            MyIStack<IStmt> stk5 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl5 =
                    new MyDictionary<String, Value>();
            MyIList<Value> out5 = new MyList<Value>();
            MyHeapDictionary hpTbl5 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl5 = new MyDictionary<String, BufferedReader>();
            MyIDictionary<String, Type> typeEnv5 = new MyDictionary<String, Type>();
            ex5.typecheck(typeEnv5);
            PrgState prg5 = new PrgState(stk5, symtbl5, out5, flTbl5, hpTbl5, ex5);
            IRepository repo5 = new Repository("log2.txt");
            repo5.add(prg5);
            Controller ctr5 = new Controller(repo5);

            programsListView.getItems().add(new RunCommand("5",ex5.toString(),ctr5));
        }
        catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex5.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        IStmt ex6 = new CompStmt( new VarDeclStmt("r", new RefType((new IntType()))), new CompStmt(new NewHeapStmt("r", new ValueExp(new IntValue(5))),
                new WriteHeapStmt("r", new ValueExp(new IntValue(8)))));

        try {
            MyIStack<IStmt> stk6 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl6 =
                    new MyDictionary<String, Value>();
            MyIList<Value> out6 = new MyList<Value>();
            MyHeapDictionary hpTbl6 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl6 = new MyDictionary<String, BufferedReader>();
            MyIDictionary<String, Type> typeEnv6 = new MyDictionary<String, Type>();
            ex6.typecheck(typeEnv6);
            PrgState prg6 = new PrgState(stk6, symtbl6, out6, flTbl6, hpTbl6, ex6);
            IRepository repo6 = new Repository("log6.txt");
            repo6.add(prg6);
            Controller ctr6 = new Controller(repo6);

            programsListView.getItems().add(new RunCommand("6",ex6.toString(),ctr6));
        }catch (MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex6.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        IStmt ex7 = new CompStmt( new VarDeclStmt("r", new RefType((new IntType()))), new CompStmt(new NewHeapStmt("r", new ValueExp(new IntValue(20))),
                new CompStmt(new PrintStmt(new VarExp("r")), new CompStmt(new WriteHeapStmt("r", new ValueExp(new IntValue(30))),
                        new PrintStmt(new ArithExp("+", new ReadHeapExp(new VarExp("r")), new ValueExp(new IntValue(5))))))));
        try {
            MyIStack<IStmt> stk7 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl7 =
                    new MyDictionary<String, Value>();
            MyIList<Value> out7 = new MyList<Value>();
            MyHeapDictionary hpTbl7 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl7 = new MyDictionary<String, BufferedReader>();
            MyIDictionary<String, Type> typeEnv7 = new MyDictionary<String, Type>();
            ex7.typecheck(typeEnv7);
            PrgState prg7 = new PrgState(stk7, symtbl7, out7, flTbl7, hpTbl7, ex7);
            IRepository repo7 = new Repository("log7.txt");
            repo7.add(prg7);
            Controller ctr7 = new Controller(repo7);

            programsListView.getItems().add(new RunCommand("7",ex7.toString(),ctr7));
        }
        catch (MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex7.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                //new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ReadHeapExp(new VarExp("v"))))));
        try {
            MyIStack<IStmt> stk8 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl8 =
                    new MyDictionary<String, Value>();
            MyIList<Value> out8 = new MyList<Value>();
            MyHeapDictionary hpTbl8 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl8 = new MyDictionary<String, BufferedReader>();
            MyIDictionary<String, Type> typeEnv8 = new MyDictionary<String, Type>();
            ex8.typecheck(typeEnv8);
            PrgState prg8 = new PrgState(stk8, symtbl8, out8, flTbl8, hpTbl8, ex8);
            IRepository repo8 = new Repository("log8.txt");
            repo8.add(prg8);
            Controller ctr8 = new Controller(repo8);

            programsListView.getItems().add(new RunCommand("8",ex8.toString(),ctr8));
        }
        catch (MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex8.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelExp(new VarExp("v"), RelOps.LARGER, new ValueExp(new IntValue(0))),
                        new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1))))), new PrintStmt(new VarExp("v")))));
        try {
            MyIStack<IStmt> stk9 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl9 =
                    new MyDictionary<String, Value>();
            MyIList<Value> out9 = new MyList<Value>();
            MyHeapDictionary hpTbl9 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl9 = new MyDictionary<String, BufferedReader>();
            MyIDictionary<String, Type> typeEnv9 = new MyDictionary<String, Type>();
            ex9.typecheck(typeEnv9);
            PrgState prg9 = new PrgState(stk9, symtbl9, out9, flTbl9, hpTbl9, ex9);
            IRepository repo9 = new Repository("log9.txt");
            repo9.add(prg9);
            Controller ctr9 = new Controller(repo9);

            programsListView.getItems().add(new RunCommand("9",ex9.toString(),ctr9));
        }
        catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex9.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new NewHeapStmt("a", new ValueExp(new IntValue(22))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),
                                        new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));


        try {
            MyIStack<IStmt> stk10= new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl10 =
                    new MyDictionary<String,Value>();
            MyIList<Value> out10 = new MyList<Value>();
            MyHeapDictionary hpTbl10 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl10 = new MyDictionary<String, BufferedReader>();
            MyIDictionary<String, Type> typeEnv10 = new MyDictionary<String, Type>();
            ex10.typecheck(typeEnv10);
            PrgState prg10 = new PrgState(stk10, symtbl10, out10, flTbl10, hpTbl10, ex10);
            IRepository repo10 = new Repository("log10.txt");
            repo10.add(prg10);
            Controller ctr10 = new Controller(repo10);

            programsListView.getItems().add(new RunCommand("10", ex10.toString(), ctr10));
        }
        catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex10.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        IStmt ex11 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(new VarDeclStmt("counter", new IntType()),
                new WhileStmt(new RelExp(new VarExp("counter"),RelOps.SMALLER, new ValueExp(new IntValue(10))),
                        new CompStmt(new ForkStmt(new ForkStmt(new CompStmt(new VarDeclStmt("b", new IntType()), new AssignStmt("b", new VarExp("counter"))) )), new AssignStmt("counter",
                                new ArithExp(ArithOps.PLUS, new VarExp("counter"), new ValueExp(new IntValue(1))))))));


        try {
            MyIStack<IStmt> stk11= new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl11 =
                    new MyDictionary<String,Value>();
            MyIList<Value> out11 = new MyList<Value>();
            MyHeapDictionary hpTbl11 = new MyHeapDictionary();
            MyIDictionary<String, BufferedReader> flTbl11 = new MyDictionary<String, BufferedReader>();
            MyIDictionary<String, Type> typeEnv11 = new MyDictionary<String, Type>();
            ex11.typecheck(typeEnv11);
            PrgState prg11 = new PrgState(stk11, symtbl11, out11, flTbl11, hpTbl11, ex11);
            IRepository repo11 = new Repository("log11.txt");
            repo11.add(prg11);
            Controller ctr11 = new Controller(repo11);

            programsListView.getItems().add(new RunCommand("11", ex11.toString(), ctr11));


            programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            programsListView.getSelectionModel().selectIndices(0);
            programsListView.getFocusModel().focus(0);
        }
        catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex11.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}

