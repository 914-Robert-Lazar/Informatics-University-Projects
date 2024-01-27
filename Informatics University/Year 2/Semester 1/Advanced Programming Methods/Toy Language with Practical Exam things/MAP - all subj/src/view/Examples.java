package view;

import model.Expressions.*;
import model.Statements.*;
import model.Types.BoolType;
import model.Types.IntType;
import model.Types.RefType;
import model.Types.StringType;
import model.Values.BoolValue;
import model.Values.IntValue;
import model.Values.RefValue;
import model.Values.StringValue;

public class Examples {

    StatementInterface example1 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(2))),
                    new PrintStatement(new VariableExpression("v"))
            )
    );
    StatementInterface example2 = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("b", new IntType()),
                    new CompoundStatement(
                            new AssignStatement(
                                    "a",
                                    new ArithmeticExpression(
                                            '+',
                                            new ValueExpression(new IntValue(2)),
                                            new ArithmeticExpression('*',
                                                    new ValueExpression(new IntValue(3)),
                                                    new ValueExpression(new IntValue(5))
                                            )
                                    )
                            ),
                            new CompoundStatement(
                                    new AssignStatement(
                                            "b",
                                            new ArithmeticExpression('+',
                                                    new VariableExpression("a"),
                                                    new ValueExpression(new IntValue(1))
                                            )
                                    ),
                                    new PrintStatement(new VariableExpression("b"))
                            )
                    )
            )
    );
    StatementInterface example3 = new CompoundStatement(
            new VariableDeclarationStatement("a", new BoolType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(
                                    new IfStatement(
                                            new VariableExpression("a"),
                                            new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                            new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            )
    );

    StatementInterface example4 = new CompoundStatement(
            new VariableDeclarationStatement("varf", new StringType()),
            new CompoundStatement(
                    new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                    new CompoundStatement(
                            new OpenFileStatement(new VariableExpression("varf")),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("varc", new IntType()),
                                    new CompoundStatement(
                                            new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("varc")),
                                                    new CompoundStatement(
                                                           new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                           new CompoundStatement(
                                                                   new PrintStatement(new VariableExpression("varc")),
                                                                   new CloseFileStatement(new VariableExpression("varf"))
                                                           )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    StatementInterface example5 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new PrintStatement(new VariableExpression("a"))
                                    )
                            )
                    )
            )
    );

    StatementInterface example6 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                            new PrintStatement(
                                                    new ArithmeticExpression(
                                                            '+',
                                                                new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                                new ValueExpression(new IntValue(5))
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    StatementInterface example7 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                            new CompoundStatement(
                                    new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                    new PrintStatement(new ArithmeticExpression(
                                            '+',
                                                new ReadHeapExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5))
                                    ))
                            )
                    )
            )
    );

    StatementInterface example8 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(
                                                    new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))
                                            )
                                    )
                            )
                    )
            )
    );

    StatementInterface example9 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(4))),
                    new CompoundStatement(
                            new WhileStatement(
                                    new RelationalExpression(
                                            ">",
                                            new VariableExpression("v"),
                                            new ValueExpression(new IntValue(0))),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new AssignStatement(
                                                    "v",
                                                    new ArithmeticExpression(
                                                            '-',
                                                            new VariableExpression("v"),
                                                            new ValueExpression(new IntValue(1)))
                                            )
                                    )
                            ),
                            new PrintStatement(new VariableExpression("v"))
                    )
            )
    );


    StatementInterface example10 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("a", new RefType(new IntType())),
                    new CompoundStatement(
                            new AssignStatement("v", new ValueExpression(new IntValue(10))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                    new CompoundStatement(
                                            new ForkStatement(
                                                    new CompoundStatement(
                                                            new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                            new CompoundStatement(
                                                                    new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new VariableExpression("v")),
                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("v")),
                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                            )
                                    )
                            )
                    )
            )
    );

    /////////////////////////////////////////////////////
    // for statement
    StatementInterface example11 = new CompoundStatement(
            new VariableDeclarationStatement("a", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("a", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new ForStatement(
                                    new ValueExpression(new IntValue(0)),
                                    new ValueExpression(new IntValue(3)),
                                    new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))),
                                    new ForkStatement(new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new AssignStatement("v", new ArithmeticExpression('*', new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a"))))
                                    )
                                    )),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                    )
            )
    );

    // lock statements
    StatementInterface example12 = new CompoundStatement(
            new VariableDeclarationStatement("v1", new RefType(new IntType())),
            new CompoundStatement(
                    new VariableDeclarationStatement("v2", new RefType(new IntType())),
                    new CompoundStatement(
                            new VariableDeclarationStatement("x", new IntType()),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("q", new IntType()),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("v1", new ValueExpression(new IntValue(20))),
                                            new CompoundStatement(
                                                    new HeapAllocationStatement("v2", new ValueExpression(new IntValue(30))),
                                                    new CompoundStatement(
                                                            new NewLockStatement("x"),
                                                            new CompoundStatement(
                                                                    new ForkStatement(
                                                                            new CompoundStatement(
                                                                                new ForkStatement(
                                                                                        new CompoundStatement(
                                                                                                new LockStatement("x"),
                                                                                                new CompoundStatement(
                                                                                                        new HeapWritingStatement("v1", new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                                                                        new UnlockStatement("x")
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        new LockStatement("x"),
                                                                                        new CompoundStatement(
                                                                                                new HeapWritingStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                                new UnlockStatement("x")
                                                                                )
                                                                            )
                                                                    )
                                                            ),
                                                                    new CompoundStatement(
                                                                            new NewLockStatement("q"),
                                                                            new CompoundStatement(
                                                                                new ForkStatement(
                                                                                        new CompoundStatement(
                                                                                                new ForkStatement(
                                                                                                        new CompoundStatement(
                                                                                                                new LockStatement("q"),
                                                                                                                new CompoundStatement(
                                                                                                                        new HeapWritingStatement("v2", new ArithmeticExpression('+', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(5)))),
                                                                                                                        new UnlockStatement("q")
                                                                                                                )
                                                                                                        )
                                                                                                ),
                                                                                                new CompoundStatement(
                                                                                                        new LockStatement("q"),
                                                                                                        new CompoundStatement(
                                                                                                                new HeapWritingStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                                new UnlockStatement("q")
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        new NopStatement(),
                                                                                        new CompoundStatement(
                                                                                                new NopStatement(),
                                                                                                new CompoundStatement(
                                                                                                        new NopStatement(),
                                                                                                        new CompoundStatement(
                                                                                                                new NopStatement(),
                                                                                                                new CompoundStatement(
                                                                                                                        new LockStatement("x"),
                                                                                                                        new CompoundStatement(
                                                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                                                                new CompoundStatement(
                                                                                                                                        new UnlockStatement("x"),
                                                                                                                                        new CompoundStatement(
                                                                                                                                               new LockStatement("q"),
                                                                                                                                               new CompoundStatement(
                                                                                                                                                       new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                                                       new UnlockStatement("q")
                                                                                                                                               )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    // conditional assignment statement
    StatementInterface example13 = new CompoundStatement(
            new VariableDeclarationStatement("a", new RefType(new IntType())),
            new CompoundStatement(
                    new VariableDeclarationStatement("b", new RefType(new IntType())),
                    new CompoundStatement(
                            new VariableDeclarationStatement("v", new IntType()),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a", new ValueExpression(new IntValue(0))),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("b", new ValueExpression(new IntValue(0))),
                                            new CompoundStatement(
                                                    new HeapWritingStatement("a", new ValueExpression(new IntValue(1))),
                                                    new CompoundStatement(
                                                            new HeapWritingStatement("b", new ValueExpression(new IntValue(2))),
                                                            new CompoundStatement(
                                                                    new ConditionalAssignmentStatement(
                                                                            "v",
                                                                            new RelationalExpression("<", new ReadHeapExpression(new VariableExpression("a")), new ReadHeapExpression(new VariableExpression("b"))),
                                                                            new ValueExpression(new IntValue(100)),
                                                                            new ValueExpression(new IntValue(200))),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new VariableExpression("v")),
                                                                            new CompoundStatement(
                                                                                    new ConditionalAssignmentStatement(
                                                                                            "v",
                                                                                            new RelationalExpression(">", new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("b")), new ValueExpression(new IntValue(2))), new ReadHeapExpression(new VariableExpression("a"))),
                                                                                            new ValueExpression(new IntValue(100)),
                                                                                            new ValueExpression(new IntValue(200))),
                                                                                    new PrintStatement(new VariableExpression("v"))
                                                                            )
                                                                    )
                                                            )
                                                            )
                                            )
                                            )
                            )
                    )
            )
    );

    // latch
    StatementInterface example14 = new CompoundStatement(
        new VariableDeclarationStatement("v1", new RefType(new IntType())),
        new CompoundStatement(
                new VariableDeclarationStatement("v2", new RefType(new IntType())),
                new CompoundStatement(
                        new VariableDeclarationStatement("v3", new RefType(new IntType())),
                        new CompoundStatement(
                                new VariableDeclarationStatement("cnt", new IntType()),
                                new CompoundStatement(
                                        new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2))),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("v2", new ValueExpression(new IntValue(3))),
                                                new CompoundStatement(
                                                        new HeapAllocationStatement("v3", new ValueExpression(new IntValue(4))),
                                                        new CompoundStatement(
                                                                new NewLatchStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                                                                new CompoundStatement(
                                                                        new ForkStatement(
                                                                                new CompoundStatement(
                                                                                        new HeapWritingStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                        new CompoundStatement(
                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                                new CompoundStatement(
                                                                                                        new CountDownStatement("cnt"),
                                                                                                        new ForkStatement(
                                                                                                                new CompoundStatement(
                                                                                                                        new HeapWritingStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                                        new CompoundStatement(
                                                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                                new CompoundStatement(
                                                                                                                                        new CountDownStatement("cnt"),
                                                                                                                                        new ForkStatement(
                                                                                                                                                new CompoundStatement(
                                                                                                                                                        new HeapWritingStatement("v3", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))),
                                                                                                                                                        new CompoundStatement(
                                                                                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))),
                                                                                                                                                                new CountDownStatement("cnt")
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),
                                                                        new CompoundStatement(
                                                                                new AwaitStatement("cnt"),
                                                                                new CompoundStatement(
                                                                                        new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                        new CompoundStatement(
                                                                                                new CountDownStatement("cnt"),
                                                                                                new PrintStatement(new ValueExpression(new IntValue(100)))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                        )
                                                )
                                )
                        )
                )
        )
    );

    // switch
    StatementInterface example15 = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("b", new IntType()),
                    new CompoundStatement(
                            new VariableDeclarationStatement("c", new IntType()),
                            new CompoundStatement(
                                    new AssignStatement("a", new ValueExpression(new IntValue(1))),
                                    new CompoundStatement(
                                            new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                            new CompoundStatement(
                                                    new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                    new CompoundStatement(
                                                            new SwitchStatement(
                                                                    new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
                                                                    new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")),
                                                                    new ValueExpression(new IntValue(10)),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new VariableExpression("a")),
                                                                            new PrintStatement(new VariableExpression("b"))
                                                                    ),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                            new PrintStatement(new ValueExpression(new IntValue(200)))
                                                                    ),
                                                                    new PrintStatement(new ValueExpression(new IntValue(300)))
                                                            ),
                                                            new PrintStatement(new ValueExpression(new IntValue(300)))
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    // count semaphore
    StatementInterface example16 = new CompoundStatement(
            new VariableDeclarationStatement("v1", new RefType(new IntType())),
            new CompoundStatement(
                    new VariableDeclarationStatement("cnt", new IntType()),
                    new CompoundStatement(
                            new HeapAllocationStatement("v1", new ValueExpression(new IntValue(1))),
                            new CompoundStatement(
                                    new CreateCountSemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1"))),
                                    new CompoundStatement(
                                            new ForkStatement(
                                                    new CompoundStatement(
                                                            new AquireCountStatement("cnt"),
                                                            new CompoundStatement(
                                                                    new HeapWritingStatement(
                                                                            "v1",
                                                                            new ArithmeticExpression(
                                                                                    '*',
                                                                                    new ReadHeapExpression(new VariableExpression("v1")),
                                                                                    new ValueExpression(new IntValue(10))
                                                                            )
                                                                    ),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                            new ReleaseCountStatement("cnt")
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new CompoundStatement(
                                                    new ForkStatement(
                                                            new CompoundStatement(
                                                                    new AquireCountStatement("cnt"),
                                                                    new CompoundStatement(
                                                                            new HeapWritingStatement(
                                                                                    "v1",
                                                                                    new ArithmeticExpression(
                                                                                            '*',
                                                                                            new ReadHeapExpression(new VariableExpression("v1")),
                                                                                            new ValueExpression(new IntValue(10))
                                                                                    )
                                                                            ),
                                                                            new CompoundStatement(
                                                                                    new CompoundStatement(
                                                                                            new HeapWritingStatement(
                                                                                                    "v1",
                                                                                                    new ArithmeticExpression(
                                                                                                            '*',
                                                                                                            new ReadHeapExpression(new VariableExpression("v1")),
                                                                                                            new ValueExpression(new IntValue(2))
                                                                                                    )
                                                                                            ),
                                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v1")))
                                                                                    ),
                                                                                    new ReleaseCountStatement("cnt")
                                                                            )
                                                                    )
                                                            )
                                                    ),
                                                    new CompoundStatement(
                                                            new AquireCountStatement("cnt"),
                                                            new CompoundStatement(
                                                                    new PrintStatement(new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                                    new ReleaseCountStatement("cnt")
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    // toy semaphore
    StatementInterface example17 = new CompoundStatement(
            new VariableDeclarationStatement("v1", new RefType(new IntType())),
            new CompoundStatement(
                    new VariableDeclarationStatement("cnt", new IntType()),
                    new CompoundStatement(
                            new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2))),
                            new CompoundStatement(
                                    new NewToySemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1))),
                                    new CompoundStatement(
                                            new ForkStatement(
                                                    new CompoundStatement(
                                                            new AquireToyStatement("cnt"),
                                                            new CompoundStatement(
                                                                    new HeapWritingStatement(
                                                                            "v1",
                                                                            new ArithmeticExpression(
                                                                                    '*',
                                                                                    new ReadHeapExpression(new VariableExpression("v1")),
                                                                                    new ValueExpression(new IntValue(10))
                                                                            )
                                                                    ),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                            new ReleaseToyStatement("cnt")
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new CompoundStatement(
                                                    new ForkStatement(
                                                            new CompoundStatement(
                                                                    new AquireToyStatement("cnt"),
                                                                    new CompoundStatement(
                                                                            new HeapWritingStatement(
                                                                                    "v1",
                                                                                    new ArithmeticExpression(
                                                                                            '*',
                                                                                            new ReadHeapExpression(new VariableExpression("v1")),
                                                                                            new ValueExpression(new IntValue(10))
                                                                                    )
                                                                            ),
                                                                            new CompoundStatement(
                                                                                    new CompoundStatement(
                                                                                            new HeapWritingStatement(
                                                                                                    "v1",
                                                                                                    new ArithmeticExpression(
                                                                                                            '*',
                                                                                                            new ReadHeapExpression(new VariableExpression("v1")),
                                                                                                            new ValueExpression(new IntValue(2))
                                                                                                    )
                                                                                            ),
                                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v1")))
                                                                                    ),
                                                                                    new ReleaseToyStatement("cnt")
                                                                            )
                                                                    )
                                                            )
                                                    ),
                                                    new CompoundStatement(
                                                            new AquireToyStatement("cnt"),
                                                            new CompoundStatement(
                                                                    new PrintStatement(new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                                    new ReleaseToyStatement("cnt")
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    // sleep stmt
    StatementInterface example18 = new CompoundStatement(
            new VariableDeclarationStatement("v",new IntType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(10))),
                    new CompoundStatement(
                            new ForkStatement(
                                    new CompoundStatement(
                                        new AssignStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))),
                                        new CompoundStatement(
                                                new AssignStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))),
                                                new PrintStatement(new VariableExpression("v"))
                                        )
                                    )
                            ),
                            new CompoundStatement(
                                    new SleepStatement(new ValueExpression(new IntValue(10))),
                                    new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))
                            )
                    )
            )
    );

    public StatementInterface[] exampleList(){
        return new StatementInterface[]{example1, example2, example3, example4, example5, example6, example7, example8,
        example9, example10, example11, example12, example13, example14, example15, example16, example17, example18};
    }
}
