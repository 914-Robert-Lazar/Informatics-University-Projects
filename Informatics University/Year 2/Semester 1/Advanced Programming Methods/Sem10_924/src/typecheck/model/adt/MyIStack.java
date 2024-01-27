package typecheck.model.adt;

public interface MyIStack<T> {

    void push (T elem);
    T pop();

    boolean isEmpty();
}
