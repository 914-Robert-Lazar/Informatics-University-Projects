package typecheck.view;

        import typecheck.controller.Controller;
        import typecheck.model.PrgState;
        import typecheck.model.adt.*;
        import typecheck.model.stmt.IStmt;
        import typecheck.model.type.Type;
        import typecheck.model.value.Value;
        import typecheck.repository.IRepository;
        import typecheck.repository.Repository;

public class RunExample1 extends Command {
    private IStmt originalProgram;

    public RunExample1(String key, String desc, IStmt originalProgram){
        super(key, desc);

        this.originalProgram = originalProgram;
    }
    @Override
    public void execute() {
        try{//compile time check
            originalProgram.typecheck(new MyDictionary<String, Type>());
            MyIStack<IStmt> stk1= new MyStack<>();
            MyIDictionary<String, Value> symtbl1 = new MyDictionary<>();
            MyIDictionaryHeap<Value> heaptbl1 = new MyDictionaryHeap<>();
            MyDictionaryHeap.nextAddress=0;//not appropriate to be public...
            MyIList<Value> out1 = new MyList<>();

            PrgState prgState1 = new PrgState(stk1, symtbl1, heaptbl1, out1, originalProgram);
            IRepository repo1 = new Repository(prgState1);

            Controller ctrl=new Controller(repo1);
            //run time checks
            ctrl.allStep();
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }
}
