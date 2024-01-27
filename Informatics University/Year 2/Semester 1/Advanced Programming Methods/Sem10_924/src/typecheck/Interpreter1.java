package typecheck;

import typecheck.model.exp.ValueExp;
import typecheck.model.exp.VarExp;
import typecheck.model.stmt.CompStmt;
import typecheck.model.stmt.IStmt;
import typecheck.model.stmt.NewStmt;
import typecheck.model.stmt.VarDeclStmt;
import typecheck.model.type.IntType;
import typecheck.model.type.RefType;
import typecheck.model.value.IntValue;
import typecheck.view.ExitCommand;
import typecheck.view.RunExample1;
import typecheck.view.TextMenu;

public class Interpreter1 {

    public static void main(String[] v){
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                    new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                            new CompStmt( new NewStmt("a", new ValueExp(new IntValue(10))),
                                    new CompStmt( new NewStmt("a", new ValueExp(new IntValue(200))),
                                            new NewStmt("a", new ValueExp(new IntValue(300))
                    )))));

        IStmt ex2= new CompStmt(new VarDeclStmt("v",new RefType( new IntType())),
                new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                        new CompStmt( new NewStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt( new NewStmt("a", new VarExp("v")),
                                        new NewStmt("v", new ValueExp(new IntValue(200)))
                                        ))));

        IStmt ex2bis= new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt( new NewStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt( new NewStmt("a", new VarExp("v")),
                                        new NewStmt("v", new ValueExp(new IntValue(200)))
                                ))));

        IStmt ex3= new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                        new CompStmt( new NewStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt( new NewStmt("a", new VarExp("v")),
                                        new CompStmt( new NewStmt("v", new ValueExp(new IntValue(200))),
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
        menu.addCommand(new RunExample1("2bis",ex2bis.toString(),ex2bis));//other prg
        menu.addCommand(new RunExample1("3",ex3.toString(),ex3));//other prg
        menu.show();
    }
}
