package Controller;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import Model.ProgramStateComponents.IHeap;
import Model.ProgramStateComponents.ProgramState;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.IRepository;

public class Controller {
    private IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void addProgramToRepository(ProgramState programState) {
        this.repository.add(programState);
    } 

    public void oneStepForAllProgramStates(List<ProgramState> programStates) throws InterruptedException{
        List<Callable<ProgramState>> callList = programStates.stream().map((ProgramState p) -> (Callable<ProgramState>)(()-> {
            return p.oneStep();
        })).collect(Collectors.toList());

        List<ProgramState> newProgramStates = executor.invokeAll(callList).stream().map(future -> {
            try { return future.get(); }
            catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).filter(p -> p != null).collect(Collectors.toList());
        programStates.addAll(newProgramStates);
        programStates.forEach(program -> {
            try {
                repository.logProgramState(program);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        repository.setProgramList(programStates);
    }

    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramList());

        programStates.forEach(program -> {
            try {
                repository.logProgramState(program);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        while (programStates.size() > 0) {
            oneStepForAllProgramStates(programStates);
            for (ProgramState state:programStates
            ) {
                state.getHeap().setContent(safeGarbageCollector(getAllAdresses(state.getSymTable().getMap().values(), state.getHeap()),state.getHeap().getContent()));
            }
            programStates = removeCompletedPrograms(repository.getProgramList());
        }

        executor.shutdownNow();
        repository.setProgramList(programStates);
    }

    // public void allStepByStep() throws MyException, IOException {
    //     ProgramState programState = repository.getCurrentProgram();
    //     System.out.println(programState);

    //     while (!programState.getExecutionStack().isEmpty()) {
    //         oneStep(programState);
    //         programState.getHeap().setContent(safeGarbageCollector(
    //             getAllAdresses(programState.getSymTable().getMap().values(), programState.getHeap()),
    //             programState.getHeap().getContent()));
    //         this.repository.logProgramState();
    //         System.out.println(programState + "\n");
    //     }
    //     this.repository.removeCurrentProgram();
    // }

    // public void allStep() throws MyException, IOException {
    //     ProgramState programState = repository.getCurrentProgram();

    //     while (!programState.getExecutionStack().isEmpty()) {
    //         oneStep(programState);
    //         programState.getHeap().setContent(safeGarbageCollector(
    //             getAllAdresses(programState.getSymTable().getMap().values(), programState.getHeap()),
    //             programState.getHeap().getContent()));
    //         this.repository.logProgramState();
    //     }
    //     System.out.println(programState);
    //     this.repository.removeCurrentProgram();
    // }

    List<Integer> getAllAdresses(Collection<Value> symbolTableValues, IHeap<Value> heap) {
        ConcurrentLinkedDeque<Integer> symbolTableAdresses = symbolTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        // System.out.println("symTable adr: " + symbolTableAdresses);

        symbolTableAdresses.stream()
                .forEach(a-> {
                    Value v = heap.getContent().get(a);
                    if (v instanceof ReferenceValue) {
                        if (!symbolTableAdresses.contains(((ReferenceValue)v).getAddress())) {
                            symbolTableAdresses.add(((ReferenceValue)v).getAddress());
                        }
                    }
                });

        // System.out.println("symTable ~intersected heapTable adr: " + symbolTableAdresses);

        return symbolTableAdresses.stream().toList();
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddreses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e->symbolTableAddreses.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        List<ProgramState> newList = new Vector<>();
        for (ProgramState program : inProgramList) {
            if (program.isNotCompleted()) {
                newList.add(program);
            }
            else {
                System.out.println(program.toString() + "\n");
            }
        }
        return newList;
    }
}
