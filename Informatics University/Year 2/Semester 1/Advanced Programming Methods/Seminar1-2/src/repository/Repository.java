package repository;
import model.Item;

public class Repository implements IRepository{
    private Item[] items;
    private int size = 0;

    Repository() {
        items = new Item[10];
    }

    public void add(Item item, int size){
        this.items[this.size++] = item;
    }
}
