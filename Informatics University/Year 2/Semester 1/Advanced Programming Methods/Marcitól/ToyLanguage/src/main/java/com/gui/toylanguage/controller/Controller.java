package com.gui.toylanguage.controller;

import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.values.RefValue;
import com.gui.toylanguage.model.values.Value;
import com.gui.toylanguage.repository.IRepository;
import com.gui.toylanguage.model.programState.PrgState;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }
    public IRepository getRepo() {
        return repo;
    }
    public synchronized void runSteps(Stepflag flag) throws MyException {
        executor = Executors.newFixedThreadPool(10);
        List<PrgState> prgList = repo.getPrgList();
        //All steps at once, only show the end state
        if(flag == Stepflag.ALLSTEPS){
            //here you can display the prg state
            while (prgList.size() > 0){

                oneStepForAllPrg(prgList);
                prgList.get(0).getHeapTable().setContent(safeGarbageCollector(getAllAddresses(getAllSymTableValues(), prgList.get(0).getHeapTable()), //get(0) since all prg uses the same heap
                        prgList.get(0).getHeapTable().getDictionary()));
                prgList = removeCompletedPrg(repo.getPrgList());
            }
            executor.shutdownNow();
            repo.setPrgList(prgList);
        }
        else if (flag == Stepflag.EACHSTEP){ //onestep at once chill
            if (prgList.size() > 0){
                oneStepForAllPrg(prgList);
                prgList.get(0).getHeapTable().setContent(safeGarbageCollector(getAllAddresses(getAllSymTableValues(), prgList.get(0).getHeapTable()), //get(0) since all prg uses the same heap
                        prgList.get(0).getHeapTable().getDictionary()));
                prgList = removeCompletedPrg(repo.getPrgList());
                repo.setPrgList(prgList);
                if(prgList.size() == 0)
                    executor.shutdownNow();
            }
            else {
                throw new MyException("prgList is empty! Nothing to run.");
            }
        }
    }

    synchronized void oneStepForAllPrg(List<PrgState> prgList) throws MyException{
        //logging
        for (PrgState prgState : prgList) {
            repo.logPrgStateExec(prgState);
        }

        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p) -> (Callable<PrgState>)(()->{return p.oneStep();})).collect(Collectors.toList());
        try{
            List<PrgState> newPrgList = executor.invokeAll(callList).stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).filter(p->p!=null).collect(Collectors.toList());
            prgList.addAll(newPrgList);
        }
        catch(InterruptedException e){
        }

        //logging
        for (PrgState prgState : prgList) {
            repo.logPrgStateExec(prgState);
        }
        //save the current program in the repository
        repo.setPrgList(prgList);
    }
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(prg -> prg.isNotCompleted()).collect(Collectors.toList());
    }

    synchronized Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, HashMap<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    synchronized Collection<Value> getAllSymTableValues(){
        Collection<Value> res = new LinkedList<>();
        repo.getPrgList().stream().forEach(prgState -> {
            for(Map.Entry<String, Value> entry : prgState.getSymTable().getDictionary().entrySet()){
                if(!res.contains(entry.getValue()))
                    res.add(entry.getValue());
            }
        });

        return res;
    }
    synchronized List<Integer> getAllAddresses(Collection<Value> symTableValues, MyHeapDictionary heapTable){
        //avem nevoie de o structura cu acces concurent (LinkedList nu e suficient)
        ConcurrentLinkedDeque<Integer> symTableAdr = symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        //procesarea elementelor din lista + posibilitatea de a adauga elemente in stream care sa fie prelucrate
        //==> acces concurent
        symTableAdr.stream()
                .forEach(a-> {
                    Value v= heapTable.getDictionary().get(a); //it can be refvalue
                    if (v instanceof  RefValue)
                        if (!symTableAdr.contains(((RefValue)v).getAddress())) //if it is in the heaptable that it points to but not in the symtableaddr then add it
                            symTableAdr.add(((RefValue)v).getAddress());});

        return symTableAdr.stream().toList();//conversie de la ConcurrentLinkedDeque la List
    }
}
