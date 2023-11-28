package textmenu;

import textmenu.model.exp.ValueExp;
import textmenu.model.exp.VarExp;
import textmenu.model.stmt.basic.CompStmt;
import textmenu.model.stmt.basic.IStmt;
import textmenu.model.stmt.heap.NewStmt;
import textmenu.model.stmt.basic.VarDeclStmt;
import textmenu.model.type.IntType;
import textmenu.model.type.RefType;
import textmenu.model.value.IntValue;
import textmenu.view.ExitCommand;
import textmenu.view.RunExample1;
import textmenu.view.TextMenu;

public class Interpreter1 {

    public static void main(String[] v){
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                    new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                            new CompStmt( new NewStmt("a", new ValueExp(new IntValue(10))),
                                    new CompStmt( new NewStmt("a", new ValueExp(new IntValue(200))),
                                            new NewStmt("a", new ValueExp(new IntValue(300))
                    )))));

        IStmt ex2= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt( new NewStmt("a", new ValueExp(new IntValue(10))),
                                new CompStmt( new NewStmt("a", new VarExp("v")),
                                        new NewStmt("a", new ValueExp(new IntValue(200)))
                                        ))));


        IStmt ex3= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt( new NewStmt("a", new ValueExp(new IntValue(10))),
                                new CompStmt( new NewStmt("a", new VarExp("v")),
                                        new CompStmt( new NewStmt("a", new ValueExp(new IntValue(200))),
                                                new NewStmt("a", new VarExp("v"))
                                )))));


        IStmt ex4= new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt( new NewStmt("v", new ValueExp(new IntValue(10))),
                                        new NewStmt("v", new ValueExp(new IntValue(200)))
                                )));

        IStmt ex5= new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt( new NewStmt("a", new ValueExp(new IntValue(10))),
                                      new NewStmt("a", new ValueExp(new IntValue(200)))
                        )));

        IStmt ex6= new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                            new CompStmt( new NewStmt("a", new VarExp("v")),
                                new CompStmt( new NewStmt("v", new ValueExp(new IntValue(30))),
                                              new NewStmt("a", new VarExp("v"))
                        )))));
//        MyIStack<IStmt> stk1= new MyStack<>();
//        MyIDictionary<String, Value> symtbl1 = new MyDictionary<>();
//        MyIDictionaryHeap<Value> heaptbl1 = new MyDictionaryHeap<>();
//        MyIList<Value> out1 = new MyList<>();
//
//        PrgState prgState1 = new PrgState(stk1, symtbl1, heaptbl1, out1, ex1);
//        IRepository repo1 = new Repository(prgState1);
//        Controller ctrl1 = new Controller(repo1);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample1("1",ex1.toString(),ex1));
        menu.addCommand(new RunExample1("2",ex2.toString(),ex2));//other prg
        menu.addCommand(new RunExample1("3",ex3.toString(),ex3));//other prg
        menu.addCommand(new RunExample1("4",ex4.toString(),ex4));//other prg
        menu.addCommand(new RunExample1("5",ex5.toString(),ex5));//other prg
        menu.addCommand(new RunExample1("6",ex6.toString(),ex6));//other prg
        menu.show();
    }
}

