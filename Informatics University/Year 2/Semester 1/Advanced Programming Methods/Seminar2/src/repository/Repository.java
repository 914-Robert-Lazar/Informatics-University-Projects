package repository;

import model.Item;
import repository.IRepository;

public class Repository implements IRepository {
    private Item[] items = null;
    private int size = 0;
    public Repository(){
        items = new Item[10];
    }
    public void add(Item item)
    {
        // if size == items.length
        items[this.size++] = item;
    }
    public void remove(Item item)
    {

    }
    public Item[] all()
    {
        return items;
    }
}
