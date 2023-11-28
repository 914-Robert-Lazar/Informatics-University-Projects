package textmenu.model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements  MyIList<T>{

    private List<T> list;

    public MyList() {
        this.list = new ArrayList<T>();
    }

    @Override
    public String toString() {
        return "MyList:"+list.toString();
    }
}