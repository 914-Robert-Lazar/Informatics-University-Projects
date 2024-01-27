package model.Expressions;

import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Types.BoolType;
import model.Types.IntType;
import model.Types.Type;
import model.Values.BoolValue;
import model.Values.IntValue;
import model.Values.Value;

public class RelationalExpression implements Expression{
    private final Expression expression1;
    private final Expression expression2;
    private final String op;

    public RelationalExpression( String op, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.op = op;
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + this.op + " " + this.expression2;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws InterpreterException {
        Value v1, v2;
        v1 = expression1.eval(SymbolTable, Heap);

        if (v1.getType().equals(new IntType())) {
            v2 = expression2.eval(SymbolTable, Heap);
            if (v2.getType().equals(new IntType())) {
                IntValue integerValue1 = (IntValue) v1; //casting -> dintr-un tip mai mare intr-un tip mai mic
                IntValue integerValue2 = (IntValue) v2; //casting -> dintr-un tip mai mare intr-un tip mai mic
                int n1, n2;
                n1 = integerValue1.getValue();
                n2 = integerValue2.getValue();

                switch (op) {
                    case "<" -> {return new BoolValue(n1 < n2);}
                    case "<=" -> {return new BoolValue(n1 <= n2);}
                    case "==" -> {return new BoolValue(n1 == n2);}
                    case "!=" -> {return new BoolValue(n1 != n2);}
                    case ">" -> {return new BoolValue(n1 > n2);}
                    case ">=" -> {return new BoolValue(n1 >= n2);}
                    default -> throw new ExpressionException("Operation invalid!");
                }
            } else
                throw new ExpressionException("The second operand is not an integer");
        } else
            throw new ExpressionException("The first operand is not an integer");

    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(this.op, this.expression1, this.expression2);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type type1, type2;
        type1 = expression1.typecheck(table);
        type2 = expression2.typecheck(table);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new TypeException("Second operand is not an integer");
            }
        } else {
            throw new TypeException("First operand is not an integer");
        }
    }
}
