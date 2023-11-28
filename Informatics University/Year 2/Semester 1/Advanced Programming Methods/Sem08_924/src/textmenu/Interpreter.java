package textmenu;

import textmenu.controller.Controller;
import textmenu.model.PrgState;
import textmenu.model.adt.*;
import textmenu.model.stmt.basic.IStmt;
import textmenu.model.stmt.basic.VarDeclStmt;
import textmenu.model.type.IntType;
import textmenu.model.value.Value;
import textmenu.repository.IRepository;
import textmenu.repository.Repository;
import textmenu.view.ExitCommand;
import textmenu.view.RunExample;
import textmenu.view.TextMenu;

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

