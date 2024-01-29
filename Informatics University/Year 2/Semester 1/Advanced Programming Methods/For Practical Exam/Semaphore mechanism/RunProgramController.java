package com.example.toylanguage_intellij;


import com.example.toylanguage_intellij.Controller.Controller;
import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.EntriesForGui.HeapEntry;
import com.example.toylanguage_intellij.Model.EntriesForGui.LockTableEntry;
import com.example.toylanguage_intellij.Model.EntriesForGui.SemaphoreEntry;
import com.example.toylanguage_intellij.Model.EntriesForGui.SymbolTableEntry;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.*;
import com.example.toylanguage_intellij.Model.Statements.IStatement;
import com.example.toylanguage_intellij.Model.Values.StringValue;
import com.example.toylanguage_intellij.Model.Values.Value;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;

public class RunProgramController {
    private Controller selectedController;
    private ProgramState selectedProgram;


    @FXML
    private ListView<IStatement> exeStackListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableColumn<HeapEntry, String> heapAddressColumn;

    @FXML
    private TableView<HeapEntry> heapTableView;

    @FXML
    private TableColumn<HeapEntry, String> heapValueColumn;

    @FXML
    private TextField NOProgramStates;

    @FXML
    private ListView<Value> outListView;

    @FXML
    private ListView<ProgramState> programStateListView;

    @FXML
    private TableView<SymbolTableEntry> symbolTableView;

    @FXML
    private TableColumn<SymbolTableEntry, String> symbolValueColumn;

    @FXML
    private TableColumn<SymbolTableEntry, String> symbolVarNameColumn;

    @FXML
    public TableView<SemaphoreEntry> semaphoreTableView;

    @FXML
    public TableColumn<SemaphoreEntry, String> semaphoreIndexColumn;

    @FXML
    public TableColumn<SemaphoreEntry, String> semaphoreValueColumn;

    @FXML
    public TableColumn<SemaphoreEntry, String> semaphoreValueListColumn;

    @FXML
    private Button runOneStepButton;

    public void setSelectedController(Controller selectedController) {
        this.selectedController = selectedController;
        this.selectedProgram = this.selectedController.getRepository().getProgramList().getFirst();


        this.programStateListView.setCellFactory(e -> new ListCell<ProgramState>(){
            @Override
            protected void updateItem(ProgramState prgState, boolean empty) {
                super.updateItem(prgState, empty);

                if (empty || prgState == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(prgState.getId()));
                }
            }
        } );

        //output to be shown
        this.outListView.setCellFactory(e -> new ListCell<>(){
            @Override
            protected void updateItem(Value value, boolean empty) {
                super.updateItem(value, empty);

                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(value.toString());
                }
            }
        });


        //for executionstack the stmt to be shown
        this.exeStackListView.setCellFactory(e -> new ListCell<>(){
            @Override
            protected void updateItem(IStatement stmt, boolean empty) {
                super.updateItem(stmt, empty);

                if (empty || stmt == null) {
                    setText(null);
                } else {
                    setText(stmt.toString());
                }
            }
        });

        this.heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("heapAddress"));
        this.heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("heapValue"));

        this.symbolVarNameColumn.setCellValueFactory(new PropertyValueFactory<>("variableName"));
        this.symbolValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        this.semaphoreIndexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        this.semaphoreValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        this.semaphoreValueListColumn.setCellValueFactory(new PropertyValueFactory<>("list"));



        IHeap<Value> heapTable = this.selectedProgram.getHeap();
        IDictionary<StringValue, BufferedReader> fileTable = this.selectedProgram.getFileTable();
        IOutputList<Value> output = this.selectedProgram.getOut();
        ISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable = this.selectedProgram.getSemaphoreTable();

        // We update their content with the new content
        heapTable.getContent().forEach((address, value)->this.heapTableView.getItems().add(new HeapEntry(address, value)));
        fileTable.getMap().forEach((fileName, bufferedReader)->this.fileListView.getItems().add(fileName.getValue()));
        output.getOutput().forEach((value)->this.outListView.getItems().add(value));
        semaphoreTable.getContent().forEach((index, value)->this.semaphoreTableView.getItems().add(new SemaphoreEntry(index, value.getKey(), value.getValue())));

        selectedController.getRepository().getProgramList().forEach((programState)->this.programStateListView.getItems().add(programState));

        // We update the number of program states
        this.NOProgramStates.setText(Integer.toString( selectedController.getRepository().getProgramList().size()));

        //whenever a new prgState selected change executionStack, symbolTable!!
        this.programStateListView.getSelectionModel().selectedItemProperty().addListener((observable, oldstate, state)-> {
            if(state != null){
                //here one shall not update the prgStateIdsListView itself! it leads to errors!
                this.selectedProgram = state;
                showDataForSelectedPrgState(state);
            }
        });


        //now we can fill everything with data
        // fill tables that are unique for each prgState
        showDataForSelectedPrgState(selectedProgram);
    }

    @FXML
    synchronized void showDataForSelectedPrgState(ProgramState selectedProgram){
        this.heapTableView.getItems().clear();
        this.outListView.getItems().clear();
        this.symbolTableView.getItems().clear();
        this.exeStackListView.getItems().clear();
        this.fileListView.getItems().clear();
        this.semaphoreTableView.getItems().clear();


        this.NOProgramStates.setText(Integer.toString( selectedController.getRepository().getProgramList().size()));

        this.selectedProgram.getFileTable().getMap().forEach((fileName, bufferedReader)->this.fileListView.getItems().add(fileName.getValue()));

        this.selectedProgram.getOut().getOutput().forEach((value)->this.outListView.getItems().add(value));


        selectedProgram.getHeap().getContent().forEach((address, value)->this.heapTableView.getItems().add(new HeapEntry(address, value)));
        selectedProgram.getSymTable().getMap().forEach((varname, value)->{
            this.symbolTableView.getItems().add(new SymbolTableEntry(varname, value));
        });
        selectedProgram.getSemaphoreTable().getContent().forEach((index, value)->this.semaphoreTableView.getItems().add(new SemaphoreEntry(index, value.getKey(), value.getValue())));
        selectedProgram.getExecutionStack().getReversed().forEach(stmt ->{
            this.exeStackListView.getItems().add(stmt);
        });
    }

    @FXML
    void runOneStepForEach(ActionEvent event) {
        try {
            this.selectedController.oneStepAtATime();
            showDataForSelectedPrgState(selectedProgram);
            //only here we update the prgStateIdsListView!!!!
            programStateListView.getItems().clear();
            selectedController.getRepository().getProgramList().forEach(prgState -> {
                programStateListView.getItems().add(prgState);
            });
        }catch (MyException | InterruptedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
