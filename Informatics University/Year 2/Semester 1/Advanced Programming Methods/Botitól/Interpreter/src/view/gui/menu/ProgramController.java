package view.gui.menu;

import controller.Controller;
import exception.InterpreterException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.adt.Heap;
import model.adt.HeapInterface;
import model.statements.Statement;
import model.values.Value;

import java.net.URL;
import java.util.*;

public class ProgramController implements Initializable {

    private Controller controller;

    public void setController(Controller controller){
        this.controller=controller;
        populateHeap();
        populateIdList();
        populateFileList();
        populateOutTable();
    }

    @FXML
    private ListView<Statement> executionStack;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapTableAddress;

    @FXML
    private TableColumn<Pair<Integer , Value>, String> heapTableValue;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    private Button oneStepButton;

    @FXML
    private ListView<String> outTable;

    @FXML
    private ListView<Integer> programStateIndexes;

    @FXML
    private TableView<Pair<String ,Value>> symbolsTable;

    @FXML
    private TableView<Pair<Integer,Value>> heapTable;

    @FXML
    private TableColumn<Pair<String,Value>,String> symbolsTableName;

    @FXML
    private TableColumn<Pair<String,Value>,String> symbolsTableValue;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        symbolsTableName.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
        symbolsTableValue.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue().toString()));
        heapTableAddress.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableValue.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().toString()));
        oneStepButton.setOnAction(actionEvent -> {
//            ProgramState state=getSelectedProgramState();
//            if (state==null){
//                Alert newAlert=new Alert(Alert.AlertType.ERROR,"No index selected", ButtonType.CLOSE);
//                newAlert.showAndWait();
//                return;
//            }
//            boolean programStateLeft = Objects.requireNonNull(getSelectedProgramState()).getExecutionStack().isEmpty();
//            if(programStateLeft){
//                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
//                alert.showAndWait();
//                return;
//            }
            try{
                if (controller.getProgramStates().isEmpty()){
                    Alert newAlert=new Alert(Alert.AlertType.ERROR,"Nothing left to execute", ButtonType.CLOSE);
                    newAlert.showAndWait();
                    return;
                }
                else {
                    oneStepExecute();
                    update();
                    List<ProgramState> states=controller.removeCompletedProgramState(controller.getProgramStates());
                    controller.setProgramStates(states);
                    populateIdList();
                }
            }catch (InterpreterException ie){
                Alert newAlert=new Alert(Alert.AlertType.ERROR,ie.getMessage(), ButtonType.CLOSE);
                newAlert.showAndWait();
                return;
            }

        });
        programStateIndexes.setOnMouseClicked(mouseEvent -> {
            ProgramState state=getSelectedProgramState();
            if (state!=null) {
                populateStack(state);
                populateSymbolsTable(state);
            }
        });

    }
    void update(){
//        ProgramState state=getSelectedProgramState();
        populateHeap();
        populateOutTable();
        populateFileList();
//        populateStack(state);
//        populateSymbolsTable(state);
        populateIdList();
    }
    void update(ProgramState state){
        populateHeap();
        populateOutTable();
        populateFileList();
        populateStack(state);
        populateSymbolsTable(state);
        populateIdList();
    }
    void oneStepExecute() throws InterpreterException {
        controller.oneStepForAllProgramsGui();
    }

    void populateHeap(){
        if (!controller.getProgramStates().isEmpty()){
        ArrayList<Pair<Integer,Value>> ht=new ArrayList<>();
        HeapInterface<Integer,Value> hp =controller.getProgramStates().get(0).getHeap();
        for (Map.Entry<Integer,Value> ev: hp.getHeap().entrySet()
             ) {
            ht.add(new Pair<>(ev.getKey(),ev.getValue()));
        }
        heapTable.setItems(FXCollections.observableList(ht));
        heapTable.refresh();
        }
        else{
            heapTable.setItems(FXCollections.observableList(new ArrayList<>()));
            heapTable.refresh();
        }
    }
    void populateIdList(){
        programStateIndexes.getItems().clear();
        Vector<Integer> ids=new Vector<>();
        for (ProgramState state:controller.getProgramStates()
             ) {
            ids.add(state.getId());
        }
        programStateIndexes.getItems().addAll(ids);
        numberOfProgramStates.setText(String.valueOf(ids.size()));
    }
    ProgramState getSelectedProgramState(){
        int id=programStateIndexes.getSelectionModel().getSelectedIndex();
        if (id<0){
            return null;
        }
        return controller.getProgramStates().get(id);
    }
    void populateStack(ProgramState state){
        executionStack.getItems().clear();
        if (state!=null){
//            Deque<Statement> statements=new ArrayDeque<>();
//            for (Statement statement: state.getExecutionStack().getStack()
//                 ) {
//                statements.push(statement);
//            }
            ArrayList<Statement> statements=new ArrayList<>();
            for (Statement statement:state.getExecutionStack().getStack()
                 ) {
                statements.add(statement);
            }
            executionStack.getItems().addAll(statements);
            executionStack.refresh();
        }
    }
    void populateOutTable(){
        if (!controller.getProgramStates().isEmpty()) {
            Vector<String> out = new Vector<>();
            outTable.getItems().clear();
            for (Value val : controller.getProgramStates().get(0).getOut().getList()
            ) {
                out.add(val.toString());
            }
            outTable.getItems().addAll(out);
            outTable.refresh();
        }
        else {
            outTable.setItems(FXCollections.observableList(new ArrayList<>()));
            outTable.refresh();
        }
    }
    void populateSymbolsTable(ProgramState state){
        if (state!=null) {
            ArrayList<Pair<String, Value>> st = new ArrayList<>();
            DictionaryInterface<String, Value> stp = state.getSymbolsTable();
            for (Map.Entry<String, Value> entry : stp.getDictionary().entrySet()
            ) {
                st.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
            symbolsTable.setItems(FXCollections.observableList(st));
            symbolsTable.refresh();
        }
    }
    void populateFileList(){
        fileTable.getItems().clear();
        if (!controller.getProgramStates().isEmpty()) {
            fileTable.getItems().addAll(controller.getProgramStates().get(0).fileTableOut());
            fileTable.refresh();
        }else{
            fileTable.setItems(FXCollections.observableList(new ArrayList<>()));
            fileTable.refresh();
        }
    }
}
