package controller;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.adt.ListInterface;
import model.adt.StackInterface;
import model.statements.Statement;
import model.values.ReferenceValue;
import model.values.Value;
import repository.RepositoryInterface;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private ExecutorService executor;
    private RepositoryInterface<ProgramState> repository;
    private boolean displayFlag;
    public Controller(RepositoryInterface<ProgramState> repo){
        repository=repo;
        displayFlag=true;
    }
    public List<ProgramState> removeCompletedProgramState(List<ProgramState> input){
        return input.stream().filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }
    public void setProgramStates(List<ProgramState> programStates){
        repository.setProgramList(programStates);
    }
    public void oneStepForAllProgramsGui()throws InterpreterException{
        executor=Executors.newFixedThreadPool(2);
        List<ProgramState> states=removeCompletedProgramState(repository.getProgramList());
        ProgramState state=states.get(0);
        state.getHeapTable().setContent(safeGarbageCollector(getAddrFromSymTable(state.getSymbolsTable().getDictionary().values(),state.getHeapTable().getHeap()),state.getHeapTable().getHeap()));
        oneStepForAllPrograms(states);


        //states=removeCompletedProgramState(repository.getProgramList());
        //repository.setProgramList(states);
        executor.shutdownNow();


    }
    public void oneStepForAllPrograms(List<ProgramState> states) throws InterpreterException{

        List<Callable<ProgramState>> callableList = states.stream()
                .map((ProgramState state) -> (Callable<ProgramState>) (state::oneStepExecute))
                .toList();
        try {
            List<ProgramState> newProgramStates = executor.invokeAll(callableList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }).filter(Objects::nonNull).toList();
            states.addAll(newProgramStates);
        }catch (InterruptedException e){
            throw new InterpreterException(e.getMessage());
        }
//        states.forEach(state -> {
//            try {
//                repository.logProgramStateExecution(state);
//            } catch (InterpreterException e) {
//                throw new RuntimeException(e);
//            }
//        });
        repository.setProgramList(states);
        states.forEach(
                state-> {
                    try {
                        repository.logProgramStateExecution(state);
                    } catch (InterpreterException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
    public ProgramState oneStepExecute(ProgramState state)throws InterpreterException {
        StackInterface<Statement> stack=state.getExecutionStack();
        if (stack.isEmpty()) {

            throw new InterpreterException("The ExecutionStack of the program is empty");
        }
        Statement currentStatement=stack.pop();
        return currentStatement.execute(state);
    }

    public void displayProgramState(ProgramState state){
//        StackInterface<Statement> stack=state.getExecutionStack();
//        DictionaryInterface<String, Value> symbolsTable=state.getSymbolsTable();
//        ListInterface<Value> output=state.getOut();
//        System.out.println("Execution stack");
//        for (Statement statement: stack.getStack()
//             ) {
//            System.out.println(statement);
//        }
//        System.out.println("Symbols table");
//        symbolsTable.getDictionary().forEach((key,value)->{
//            System.out.println(symbolsTable.toStringElem(key,value));
//        });
//        System.out.println("Output");
//        for (Value value:output.getList()
//             ) {
//            System.out.println(value.toString());
//        }
        System.out.println(state);
    }
    public Map<Integer,Value> safeGarbageCollector(List<Integer> symbolsTableAddresses, Map<Integer,Value> heap){
        return heap.entrySet().stream().filter(elem->symbolsTableAddresses.contains(elem.getKey())).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }
    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues,Map<Integer,Value> heap){
        List<Integer> addresses=new ArrayList<>();
        symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .forEach(v-> {
                    while (v instanceof ReferenceValue){
                        addresses.add(((ReferenceValue) v).getAddress());
                        v=heap.get(((ReferenceValue) v).getAddress());
                    }
                });
        return addresses;
    }

    public void executeAllSteps() throws InterpreterException {
//        ProgramState state=repository.getCurrentProgram();
//        repository.logProgramStateExecution(state);
//        if (state.getExecutionStack().isEmpty())
//            throw new InterpreterException("The ExecutionStack of the program is empty");
//        if (displayFlag)
//            displayProgramState(state);
//        while (!state.getExecutionStack().isEmpty()){
//
//            state=oneStepExecute(state);
//            repository.logProgramStateExecution(state);
//            state.getHeapTable().setContent(safeGarbageCollector(getAddrFromSymTable(state.getSymbolsTable().getDictionary().values(),state.getHeapTable().getHeap()),state.getHeapTable().getHeap()));
//            if (displayFlag){
//                displayProgramState(state);
//            }
//            if (state.getExecutionStack().isEmpty()){
//                if (!displayFlag) {
//                    System.out.println("Output");
//                    for (Value value : state.getOut().getList()
//                    ) {
//                        System.out.println(value.toString());
//                    }
//                }
//            }
//
//        }

        executor= Executors.newFixedThreadPool(2);
        List<ProgramState> programStates=removeCompletedProgramState(repository.getProgramList());
        if (programStates.isEmpty())
            throw new InterpreterException("Program list is empty");
        programStates.forEach(
                state-> {
                    try {
                        repository.logProgramStateExecution(state);
                    } catch (InterpreterException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        while (!programStates.isEmpty()){

            oneStepForAllPrograms(programStates);
            for (ProgramState state:programStates
            ) {
                state.getHeapTable().setContent(safeGarbageCollector(getAddrFromSymTable(state.getSymbolsTable().getDictionary().values(),state.getHeapTable().getHeap()),state.getHeapTable().getHeap()));
            }
            programStates=removeCompletedProgramState(repository.getProgramList());
        }
        executor.shutdownNow();
        repository.setProgramList(programStates);

    }
    public void setDisplayFlag(boolean value){
        displayFlag=value;
    }
    public List<ProgramState> getProgramStates(){
        return repository.getProgramList();
    }
}
