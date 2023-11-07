package Model.ProgramStateComponents;

public interface ISymbolTable<Key, Val> {
    Val findValue(Key key);

    void put(Key key, Val value);

    boolean isDefined(Key key);
}
