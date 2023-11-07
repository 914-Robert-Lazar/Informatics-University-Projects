package controller;

import model.Item;
import repository.IRepository;

public class Controller {
    IRepository repository;

    public Controller(IRepository repo) {
        repository = repo;
    }
    public void add(Item item){repository.add(item);};
    public void remove(){};
    public Item[] all(){return repository.all();};
    public Item[] filter(float w){
        Item[] filteredArray = new Item[repository.all().length];
        int size = 0;
        for (Item i : repository.all())
        {
            if (i.getWeight() > w)
            {
               filteredArray[size++] = i;
            }
        }
        return filteredArray;
    };
}
