package Model.Expressions;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IHeap;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class ReadFromHeapExpression implements Expression {
    Expression expression;

    public ReadFromHeapExpression(Expression expression) {
        this.expression = expression;
    }
    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value value = expression.evaluate(table, heap);
        if (!(value instanceof ReferenceValue)) {
            throw new MyException("The expression does not evaluate to a reference value.");
        }
        else {
            int address = ((ReferenceValue) value).getAddress();
            if (!heap.isDefined(address)) {
                throw new MyException("The address is not allocated on the heap");
            }
            else {
                return heap.findValue(address);
            }
        }
    }
    
    @Override
    public String toString() {
        return "ReadHeap(" + this.expression.toString() + ")";
    }
    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        }
        else {
            throw new MyException("The read from heap argument is not a ReferenceType.");
        }
    }
}
