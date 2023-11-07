package Repository;

import java.util.LinkedList;
import java.util.Queue;

import Model.ProgramStateComponents.ProgramState;

public class Repository implements IRepository{
    Queue<ProgramState> queue;

    public Repository() {
        this.queue = new LinkedList<ProgramState>();
    }

    @Override
    public ProgramState getCurrentProgram() {
        return this.queue.poll();
    }

    @Override
    public void add(ProgramState programState) {
        this.queue.add(programState);
    }
    
}
