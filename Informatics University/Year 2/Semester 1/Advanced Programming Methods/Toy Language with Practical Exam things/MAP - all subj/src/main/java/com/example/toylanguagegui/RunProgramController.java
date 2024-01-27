package com.example.toylanguagegui;

import controller.Controller;
import exceptions.AdtException;
import exceptions.FileException;
import exceptions.InterpreterException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;
import model.ADTs.*;
import model.ProgramState;
import model.Statements.StatementInterface;
import model.Values.StringValue;
import model.Values.Value;
import repository.Repository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class RunProgramController {
    private Controller controller = null;

    @FXML
    private Button runOneStepButton;


    @FXML
    private TextField noOfPrograms;

    @FXML
    private ListView<ProgramState> programStateListView;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolTableView;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> nameSymbolTableColumn;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> valueSymbolTableColumn;

    @FXML
    private ListView<StatementInterface> exeStackListView;

    @FXML
    private ListView<Value> outListView;

    @FXML
    private ListView<StringValue> fileListView;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> addressHeapTableColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> valueHeapTableColumn;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> lockTable;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> locationLockTableColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> valueLockTableColumn;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> latchTable;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> locationLatchTableColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> valueLatchTableColumn;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, String>> > countSemaphoreTable;
    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, String> indexCountSemaphoreTableColumn;
    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, String> valueCountSemaphoreTableColumn;
    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, String> listCountSemaphoreTableColumn;

    @FXML
    private TableView<Pair<Pair<Integer, Integer>, Pair<String, Integer>> > toySemaphoreTable;
    @FXML
    private TableColumn<Pair<Pair<Integer, Integer>, Pair<String, Integer>>, String> indexToySemaphoreTableColumn;
    @FXML
    private TableColumn<Pair<Pair<Integer, Integer>, Pair<String, Integer>>, String> value1ToySemaphoreTableColumn;
    @FXML
    private TableColumn<Pair<Pair<Integer, Integer>, Pair<String, Integer>>, String> value2ToySemaphoreTableColumn;
    @FXML
    private TableColumn<Pair<Pair<Integer, Integer>, Pair<String, Integer>>, String> listToySemaphoreTableColumn;



    @FXML
    public void initialize() {
        this.runOneStepButton.setOnAction(actionEvent -> runOneStepButtonHandler());

        // when you set the value of a cell, set the text to be the id of the thread, since a cell is a program state.
        this.programStateListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ProgramState> call(ListView<ProgramState> programStateListView) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(ProgramState item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(Integer.toString(item.getId_thread()));
                        }
                        else
                            setText("");
                    }
                };
            }
        });
        // setCellValueFactory -> which is used to populate individual cells in the column
        this.nameSymbolTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getKey()));
        this.valueSymbolTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getValue().toString()));
        // for heap
        this.addressHeapTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getKey().toString()));
        this.valueHeapTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getValue().toString()));
        // for lockTable
        this.locationLockTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getKey().toString()));
        this.valueLockTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getValue().toString()));
        // for latchTable
        this.locationLatchTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getKey().toString()));
        this.valueLatchTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getValue().toString()));
        // for count semaphore
        this.indexCountSemaphoreTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getKey().toString()));
        this.valueCountSemaphoreTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getValue().getKey().toString()));
        this.listCountSemaphoreTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getValue().getValue().toString()));
        // for toy semaphore
        this.indexToySemaphoreTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getKey().getKey().toString()));
        this.value1ToySemaphoreTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getKey().getValue().toString()));
        this.listToySemaphoreTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getValue().getKey().toString()));
        this.value2ToySemaphoreTableColumn.setCellValueFactory(cellDataMapEntry -> new SimpleStringProperty(cellDataMapEntry.getValue().getValue().getValue().toString()));

        // you are allowed to select ONLY ONE id, because when you click on it, you need to change the state(because
        // every thread has a different symbol table and exe stack)
        this.programStateListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.programStateListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldState, newState) -> changeThreadState(newState)
        );
    }

    public void setStatement(StatementInterface statement, String path){
        ProgramState state = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                                              new MyDictionary<>(), new MyHeap<>(), new MyLockTable<>(),
                                              new MyCountDownLatchTable<>(), new MyCountSemaphore<>(),
                                              new MyToySemaphore<>(), statement);
        try {
            // initialize the controller
            Repository repo = new Repository(path);
            repo.addProgram(state);
            this.controller = new Controller(repo);

            // first we update because we can't do "getSelectionModel" and have nothing in the list.
            this.updateFields();
            this.programStateListView.getSelectionModel().select(0);

            this.runOneStepButton.setDisable(false);

        } catch (FileException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void runOneStepButtonHandler(){
        try {
            this.controller.oneStepForAllPrgGUI();
        } catch (InterpreterException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
            this.runOneStepButton.setDisable(true);
        } finally {
            this.updateFields();
        }
    }

    private void updateFields(){
        // nr of programs
        this.setNoOfPrograms();
        // populate the program states list (initially only one, and only with threads there will be multiple)
        this.populateStateListView();
        // populate exe stack and symbol table
        this.changeThreadState(this.programStateListView.getSelectionModel().getSelectedItem());
        // populate out
        this.populateOut();
        // populate file table
        this.populateFileList();
        // populate heap
        this.populateHeapTable();
        // populate lock table
        this.populateLockTable();
        // populate latch table
        this.populateLatchTable();
        // populate count semaphore table
        this.populateCountSemaphoreTable();
        // populate toy semaphore table
        this.populateToySemaphoreTable();
    }

    private void changeThreadState(ProgramState newState){
        this.populateSymTable(newState);
        this.populateExeStack(newState);
    }

    public void setNoOfPrograms(){
        this.noOfPrograms.setText(String.valueOf(this.controller.getAllPrograms().size()));
    }

    public void populateCountSemaphoreTable(){
        if(this.controller.getAllPrograms().size() > 0) {
            List<Pair<Integer, Pair<Integer, String>>> countSemList = new ArrayList<>();
            controller.getCountSemaphoreGUI().getContent().forEach((key, value) -> countSemList.add(new Pair<>(key, new Pair<>(value.getKey(), value.getValue().toString()))));
            this.countSemaphoreTable.setItems(FXCollections.observableList(countSemList));
        }
    }

    public void populateToySemaphoreTable(){
        if(this.controller.getAllPrograms().size() > 0) {
            List<Pair<Pair<Integer, Integer>, Pair<String, Integer>>> toySemList = new ArrayList<>();
            controller.getToySemaphoreGUI().getContent().forEach((key, value) -> toySemList.add(new Pair<>(new Pair<>(key, value.first), new Pair<>(value.second.toString(), value.third))));
            this.toySemaphoreTable.setItems(FXCollections.observableList(toySemList));
        }
    }

    public void populateLatchTable(){
        if(this.controller.getAllPrograms().size() > 0)
            this.latchTable.getItems().setAll(FXCollections.observableArrayList(this.controller.getLatchTableGUI().getContent().entrySet()));

    }
    public void populateLockTable(){
        if(this.controller.getAllPrograms().size() > 0)
            this.lockTable.getItems().setAll(FXCollections.observableArrayList(this.controller.getLockTableGUI().getContent().entrySet()));
    }

    public void populateHeapTable(){
        if(this.controller.getAllPrograms().size() > 0)
            this.heapTable.getItems().setAll(FXCollections.observableArrayList(this.controller.getHeapTableGUI().getContent().entrySet()));
    }

    public void populateFileList(){
        if(this.controller.getAllPrograms().size() > 0)
            this.fileListView.getItems().setAll(FXCollections.observableArrayList(this.controller.getFileTableGUI().getContent().keySet()));
    }

    public void populateOut(){
        // error "Index 0 out of bounds for length 0" because of .getOutGUI if you don;t put the if and you reach the end
        if(this.controller.getAllPrograms().size() > 0)
            this.outListView.getItems().setAll(FXCollections.observableArrayList(this.controller.getOutGUI().getList()));
    }

    public void populateSymTable(ProgramState state){
        if(state != null)
            this.symbolTableView.getItems().setAll(FXCollections.observableArrayList(state.getSymbolTable().getContent().entrySet()));
        else
            this.symbolTableView.getItems().setAll(FXCollections.observableArrayList());
    }

    public void populateExeStack(ProgramState state){
        if (state != null){
            ObservableList<StatementInterface> statements = FXCollections.observableArrayList();
            MyStackInterface<StatementInterface> exeStack = state.getExeStack();

            // add to the ListView every statement from the exeStack (either 0, 1 or 2)
            while(!exeStack.isEmpty()){
                try{
                    statements.add(exeStack.pop());
                } catch (AdtException e) {
                    e.printStackTrace();
                }
            }

            // after popping, we need to add them again in reverse order into the exeStack.
            IntStream.range(0, statements.size()).forEach(pos -> exeStack.push(statements.get(statements.size() - 1 - pos)));
            this.exeStackListView.getItems().setAll(statements);
        } else {
            this.exeStackListView.getItems().setAll(FXCollections.observableArrayList());
        }
    }

    public void populateStateListView(){
        ObservableList<ProgramState> ids = FXCollections.observableList(this.controller.getAllPrograms());
        this.programStateListView.setItems(ids);

    }


}
