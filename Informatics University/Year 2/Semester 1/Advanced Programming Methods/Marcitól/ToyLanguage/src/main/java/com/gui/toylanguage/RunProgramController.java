package com.gui.toylanguage;

import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.adt.MyIList;
import com.gui.toylanguage.controller.Controller;
import com.gui.toylanguage.controller.Stepflag;
import com.gui.toylanguage.dto.HeapEntry;
import com.gui.toylanguage.dto.SymTableEntry;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.statements.IStmt;
import com.gui.toylanguage.model.values.Value;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;

public class RunProgramController {

    private Controller selectedController;
    private PrgState selectedProgram;


    @FXML
    private ListView<IStmt> exeStackListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableColumn<HeapEntry, String> heapAddressColumn;

    @FXML
    private TableView<HeapEntry> heapTableView;

    @FXML
    private TableColumn<HeapEntry, String> heapValueColumn;

    @FXML
    private TextField nrOfPrgStates;

    @FXML
    private ListView<Value> outListView;

    @FXML
    private ListView<PrgState> prgStateIdsListView;

    @FXML
    private TableView<SymTableEntry> symTableView;

    @FXML
    private TableColumn<SymTableEntry, String> symValueColumn;

    @FXML
    private TableColumn<SymTableEntry, String> symVarNameColumn;

    @FXML
    private Button runOneStepButton;

    public void setSelectedController(Controller selectedController) {
        this.selectedController = selectedController;
        this.selectedProgram = this.selectedController.getRepo().getPrgList().get(0); //at the beginning we have only one prgstate

        //basically now comes the initialization after wet set the controller and got the selected program

        //prgStatesId to be shown
        this.prgStateIdsListView.setCellFactory(e -> new ListCell<PrgState>(){
            @Override
            protected void updateItem(PrgState prgState, boolean empty) {
                super.updateItem(prgState, empty);

                if (empty || prgState == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(prgState.id));
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
        //fileTable not needed either way simple string is shown only

        //for executionstack the stmt to be shown
        this.exeStackListView.setCellFactory(e -> new ListCell<>(){
            @Override
            protected void updateItem(IStmt stmt, boolean empty) {
                super.updateItem(stmt, empty);

                if (empty || stmt == null) {
                    setText(null);
                } else {
                    setText(stmt.toString());
                }
            }
        });

        this.heapAddressColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("heapAddress"));
        this.heapValueColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String >("heapValue"));

        this.symVarNameColumn.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("variableName"));
        this.symValueColumn.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("value"));

        //we fill the tables, lists, etc that are shared!!

        MyHeapDictionary heapTable = this.selectedProgram.getHeapTable();
        MyIDictionary<String, BufferedReader> fileTable = this.selectedProgram.getFileTable();
        MyIList<Value> output = this.selectedProgram.getOut();

        // We update their content with the new content
        heapTable.getDictionary().forEach((address, value)->this.heapTableView.getItems().add(new HeapEntry(address, value)));
        fileTable.getDictionary().forEach((fileName, bufferedReader)->this.fileListView.getItems().add(fileName));
        output.getList().forEach((value)->this.outListView.getItems().add(value));

        selectedController.getRepo().getPrgList().forEach((programState)->this.prgStateIdsListView.getItems().add(programState));

        // We update the number of program states
        this.nrOfPrgStates.setText(Integer.toString( selectedController.getRepo().getPrgList().size()));

        //whenever a new prgState selected change exestack, symboltable!!
        this.prgStateIdsListView.getSelectionModel().selectedItemProperty().addListener((observable,oldstate,state)-> {
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

    synchronized void showDataForSelectedPrgState(PrgState selectedProgram){
        this.heapTableView.getItems().clear();
        this.outListView.getItems().clear();
        this.symTableView.getItems().clear();
        this.exeStackListView.getItems().clear();


        this.nrOfPrgStates.setText(Integer.toString( selectedController.getRepo().getPrgList().size()));

        this.selectedProgram.getFileTable().getDictionary().forEach((fileName, bufferedReader)->this.fileListView.getItems().add(fileName));

        this.selectedProgram.getOut().getList().forEach((value)->this.outListView.getItems().add(value));


        selectedProgram.getHeapTable().getDictionary().forEach((address, value)->this.heapTableView.getItems().add(new HeapEntry(address, value)));
        selectedProgram.getSymTable().getDictionary().forEach((varname, value)->{
            System.out.println(varname);
            System.out.println(value);
            this.symTableView.getItems().add(new SymTableEntry(varname, value));
        });
        selectedProgram.getExeStack().getReversed().forEach(stmt ->{
            this.exeStackListView.getItems().add(stmt);
        });
    }

    @FXML
    void runOneStepForEach(ActionEvent event) {
        try {
            this.selectedController.runSteps(Stepflag.EACHSTEP);
            showDataForSelectedPrgState(selectedProgram);
            //only here we update the prgStateIdsListView!!!!
            prgStateIdsListView.getItems().clear();
            selectedController.getRepo().getPrgList().forEach(prgState -> {
                prgStateIdsListView.getItems().add(prgState);
            });
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
    }
}
