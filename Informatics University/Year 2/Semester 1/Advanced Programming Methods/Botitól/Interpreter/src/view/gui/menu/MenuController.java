package view.gui.menu;

import controller.Controller;
import exception.InterpreterException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.ProgramState;
import model.adt.Dictionary;
import model.adt.Heap;
import model.adt.List;
import model.adt.Stack;
import model.statements.Statement;
import model.values.StringValue;
import model.values.Value;
import repository.Repository;
import repository.RepositoryInterface;
import utils.StatementExample;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private ProgramController programController;
    public void setProgramController(ProgramController controller){
        programController=controller;
    }
    @FXML
    private ListView<Statement> programStateView;

    @FXML
    private Button pushButton;

    @FXML
    void runSelectedProgramState(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Statement ex1= Utils.buildStatement(new VariableDeclarationStatement("v",new IntType()),new AssignmentStatement("v",new ValueExpression(new IntValue(2))),new PrintStatement(new VariableExpression("v")));
//        ProgramState state= null;
//        try {
//            ex1.typeCheck(new Dictionary<>());
//
//            state = new ProgramState(new Stack<Statement>(),new Dictionary<String, Value>(),new List<Value>(),new Dictionary<StringValue, BufferedReader>(),new Heap(),ex1);
//        } catch (InterpreterException e) {
//            throw new RuntimeException(e);
//        }
//        RepositoryInterface<ProgramState> repo=new Repository<>("src/log1.txt");
//        repo.add(state);
//        Controller controller=new Controller(repo);
//        programStateView.getItems().addAll(repo.getProgramList());
        pushButton.setOnAction(actionEvent1 -> {
            int index=programStateView.getSelectionModel().getSelectedIndex();
            if (index<0){
                Alert newAlert=new Alert(Alert.AlertType.ERROR,"No index selected", ButtonType.CLOSE);
                newAlert.showAndWait();
                return;
            }
            try {
                Statement statement= StatementExample.generateStatements().get(index);
                statement.typeCheck(new Dictionary<>());
                ProgramState state=new ProgramState(new Stack<Statement>(),new Dictionary<String, Value>(),new List<Value>(),new Dictionary<StringValue, BufferedReader>(),new Heap(),statement);
                RepositoryInterface<ProgramState> repository=new Repository<>("log.txt");
                repository.add(state);
                Controller controller=new Controller(repository);
                programController.setController(controller);

            } catch (InterpreterException e) {
                Alert newAlert=new Alert(Alert.AlertType.ERROR,"No index selected", ButtonType.CLOSE);
                newAlert.showAndWait();
            }
        });
        try {
            programStateView.getItems().addAll(StatementExample.generateStatements());
        } catch (InterpreterException e) {
            throw new RuntimeException(e);
        }
    }

    public void runSelectredProgramState(ActionEvent actionEvent) {
        pushButton.setOnAction(actionEvent1 -> {
            int index=programStateView.getSelectionModel().getSelectedIndex();
            if (index<0){
                Alert newAlert=new Alert(Alert.AlertType.ERROR,"No index selected", ButtonType.CLOSE);
                newAlert.showAndWait();
                return;
            }

        });
    }

}
