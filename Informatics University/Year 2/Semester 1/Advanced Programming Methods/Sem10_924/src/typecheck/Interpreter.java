package typecheck;

import typecheck.controller.Controller;
import typecheck.model.PrgState;
import typecheck.model.adt.*;
import typecheck.model.stmt.IStmt;
import typecheck.model.stmt.VarDeclStmt;
import typecheck.model.type.IntType;
import typecheck.model.value.Value;
import typecheck.repository.IRepository;
import typecheck.repository.Repository;
import typecheck.view.ExitCommand;
import typecheck.view.RunExample;
import typecheck.view.TextMenu;

public class Interpreter {

    public static void main(String[] v){
        IStmt ex1= new VarDeclStmt("v",new IntType());



        MyIStack<IStmt> stk1= new MyStack<>();
        MyIDictionary<String, Value> symtbl1 = new MyDictionary<>();
        MyIDictionaryHeap<Value> heaptbl1 = new MyDictionaryHeap<>();
        MyIList<Value> out1 = new MyList<>();

        PrgState prgState1 = new PrgState(stk1, symtbl1, heaptbl1, out1, ex1);
        IRepository repo1 = new Repository(prgState1);
        Controller ctrl1 = new Controller(repo1);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctrl1));
        menu.addCommand(new RunExample("2",ex1.toString(),ctrl1));//other prg
        menu.addCommand(new RunExample("3",ex1.toString(),ctrl1));//other prg
        menu.show();
    }
}
