package Controller;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

import Model.ProgramStateComponents.IExecutionStack;
import Model.ProgramStateComponents.IHeap;
import Model.ProgramStateComponents.ProgramState;
import Model.Statements.IStatement;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.IRepository;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void addProgramToRepository(ProgramState programState) {
        this.repository.add(programState);
    } 

    public ProgramState oneStep(ProgramState programState) throws MyException, IOException {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();

        if (executionStack.isEmpty()) {
            throw new MyException("ProgramState Stack is empty.");
        }

        IStatement currStatement = executionStack.pop();
        return currStatement.execute(programState);
    }

    public void allStepByStep() throws MyException, IOException {
        ProgramState programState = repository.getCurrentProgram();
        System.out.println(programState);

        while (!programState.getExecutionStack().isEmpty()) {
            oneStep(programState);
            this.repository.logProgramState();
            programState.getHeap().setContent(safeGarbageCollector(
                    getAllAdresses(programState.getSymTable().getMap().values(), programState.getHeap()),
                    programState.getHeap().getContent()));
            System.out.println(programState + "\n");
        }
        this.repository.removeCurrentProgram();
    }

    public void allStep() throws MyException, IOException {
        ProgramState programState = repository.getCurrentProgram();

        while (!programState.getExecutionStack().isEmpty()) {
            oneStep(programState);
            this.repository.logProgramState();
            programState.getHeap().setContent(safeGarbageCollector(
                    getAllAdresses(programState.getSymTable().getMap().values(), programState.getHeap()),
                    programState.getHeap().getContent()));
        }
        System.out.println(programState);
        this.repository.removeCurrentProgram();
    }

    List<Integer> getAllAdresses(Collection<Value> symbolTableValues, IHeap<Value> heap) {
        ConcurrentLinkedDeque<Integer> symbolTableAdresses = symbolTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        System.out.println("symTable adr: " + symbolTableAdresses);

        symbolTableAdresses.stream()
                .forEach(a-> {
                    Value v = heap.getContent().get(a);
                    if (v instanceof ReferenceValue) {
                        if (!symbolTableAdresses.contains(((ReferenceValue)v).getAddress())) {
                            symbolTableAdresses.add(((ReferenceValue)v).getAddress());
                        }
                    }
                });

        System.out.println("symTable ~intersected heapTable adr: " + symbolTableAdresses);

        return symbolTableAdresses.stream().toList();
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddreses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e->symbolTableAddreses.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
