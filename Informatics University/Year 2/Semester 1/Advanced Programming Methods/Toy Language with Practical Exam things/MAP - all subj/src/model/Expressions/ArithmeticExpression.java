package model.Expressions;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

public class ArithmeticExpression implements Expression {
    private final Expression expression1;
    private final Expression expression2;
    private int op;  // 1-plus, 2-minus, 3-star, 4-divide

    public ArithmeticExpression(char op, Expression first, Expression second) {
        this.expression1 = first;
        this.expression2 = second;
        switch (op){
            case '+' -> this.op = 1;
            case '-' -> this.op = 2;
            case '*' -> this.op = 3;
            case '/' -> this.op = 4;
        }
    }

    @Override
    public String toString() {
        return switch (op) {
            case 1 -> expression1.toString() + "+" + expression2.toString();
            case 2 -> expression1.toString() + "-" + expression2.toString();
            case 3 -> expression1.toString() + "*" + expression2.toString();
            case 4 -> expression1.toString() + '/' + expression2.toString();
            default -> "";
        };
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws InterpreterException {
        Value v1, v2;
        v1 = expression1.eval(SymbolTable, Heap);

        if (v1.getType().equals(new IntType())) {
            v2 = expression2.eval(SymbolTable, Heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1; //casting -> dintr-un tip mai mare intr-un tip mai mic
                IntValue i2 = (IntValue) v2; //casting -> dintr-un tip mai mare intr-un tip mai mic
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();

                switch (op) {
                    case 1 -> {return new IntValue(n1 + n2);}
                    case 2 -> {return new IntValue(n1 - n2);}
                    case 3 -> {return new IntValue(n1 * n2);}
                    case 4 -> {
                        if (n2 == 0)
                            throw new ExpressionException("Division by 0!"); //DivisionBy0Exception
                        else
                            return new IntValue(n1 / n2);
                    }
                    default -> throw new ExpressionException("Operation invalid!");
                }
            } else
                throw new ExpressionException("The second operand is not an integer");
        } else
            throw new ExpressionException("The first operand is not an integer");

    }

    @Override
    public Expression deepCopy() {
        char newOp;
        switch (this.op){
            case 1 -> newOp = '+';
            case 2 -> newOp = '-';
            case 3 -> newOp = '*';
            case 4 -> newOp = '/';
            default -> newOp = ' ';
        }
        return new ArithmeticExpression(newOp, this.expression1, this.expression2);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type type1, type2;
        type1 = expression1.typecheck(table);
        type2 = expression2.typecheck(table);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new TypeException("Second operand is not an integer");
            }
        } else {
            throw new TypeException("First operand is not an integer");
        }
    }

}
