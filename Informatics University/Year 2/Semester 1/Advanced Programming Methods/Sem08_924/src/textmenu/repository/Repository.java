package textmenu.repository;

import textmenu.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    private List<PrgState> repo;

    public Repository(PrgState prgState ){
        this.repo = new ArrayList<>();
        repo.add(prgState);
    }

    @Override
    public PrgState getCrtPrg() {
        return repo.get(0);
    }
}

