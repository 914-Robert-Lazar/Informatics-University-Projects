package repository;

import model.Item;

public interface IRepository {
    abstract public void add(Item item);
    abstract public void remove(Item item);
    abstract public Item[] all();
}
