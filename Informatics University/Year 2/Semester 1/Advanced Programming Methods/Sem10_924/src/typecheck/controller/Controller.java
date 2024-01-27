package typecheck.controller;

import typecheck.model.PrgState;
import typecheck.model.adt.MyException;
import typecheck.model.adt.MyIDictionaryHeap;
import typecheck.model.adt.MyIStack;
import typecheck.model.stmt.IStmt;
import typecheck.model.value.RefValue;
import typecheck.model.value.Value;
import typecheck.repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class Controller {
    IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getMyStack();
        if(stk.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg(); // repo is the controller field of type MyRepoInterface
        System.out.println(prg);
        while (!prg.getMyStack().isEmpty()){
            oneStep(prg);
            //System.out.println(prg);
            //unsafe - sterge toate adresele care nu apar in SymTable
//            prg.getMyHeapTable().setContent(unsafeGarbageCollector(
//                    getAddrFromSymTable(prg.getMySymTable().getContent().values()),
//                    prg.getMyHeapTable().getContent()));

            //safe - incorect - sterge si adrese din heap care sunt sunt referite indirect din Heap
//            prg.getMyHeapTable().setContent(
//                    safeGarbageCollector(
//                            getAllAddresses(prg.getMySymTable().getContent().values(), prg.getMyHeapTable().getContent().values()),
//                            prg.getMyHeapTable().getContent()));

            //safe - corect, pastreaza taote adresele din heap care sunt referite indirect din Heap
            prg.getMyHeapTable().setContent(
                    safeGarbageCollector1(
                            getAllAddresses1(prg.getMySymTable().getContent().values(), prg.getMyHeapTable()),
                            prg.getMyHeapTable().getContent()));
            System.out.println(prg);
        }
    }

    // unsafe
    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    //safe- incorect, doar colecteaza adresele din symtable si heap
    List<Integer> getAllAddresses(Collection<Value> symTableValues, Collection<Value> heapTableValues){
        List<Integer> symTableAdr = symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());

        List<Integer> heaptablAdr = heapTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());

        symTableAdr.addAll(heaptablAdr);
        System.out.println(symTableAdr);
        return symTableAdr;
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }



    //safe- corect, colecteaza adresele referite direct si indirect din Symtable
    List<Integer> getAllAddresses1(Collection<Value> symTableValues, MyIDictionaryHeap<Value> heapTable){
        //avem nevoie de o structura cu acces concurent (LinkedList nu e suficient)
        ConcurrentLinkedDeque<Integer> symTableAdr = symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        //procesarea elementelor din lista + posibilitatea de a adauga elemente in stream care sa fie prelucrate
        //==> acces concurent
        symTableAdr.stream()
                .forEach(a-> {
                    Value v= heapTable.getContent().get(a);
                    if (v instanceof  RefValue)
                        if (!symTableAdr.contains(((RefValue)v).getAddr()))
                            symTableAdr.add(((RefValue)v).getAddr());});

        System.out.println(symTableAdr);
        return symTableAdr.stream().toList();//conversie de la ConcurrentLinkedDeque la List
    }


    Map<Integer, Value> safeGarbageCollector1(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
