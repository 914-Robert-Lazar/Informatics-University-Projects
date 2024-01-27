package repository;

import exception.InterpreterException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

public class Repository<ProgramState> implements RepositoryInterface<ProgramState>{

    private String path;
    private List<ProgramState> programFrames;

    public Repository(String logPath){
        programFrames= new Vector<ProgramState>();
        path=logPath;
    }
    @Override
    public ProgramState getCurrentProgram() throws InterpreterException {
        if (programFrames.isEmpty())
            throw new InterpreterException("No programs to execute");
        ProgramState state=programFrames.get(0);
        programFrames.remove(0);
        return state;
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws InterpreterException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
            logFile.println(state);
            logFile.close();
        } catch (IOException ie) {
            throw new InterpreterException(ie.toString());
        }
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programFrames;
    }

    @Override
    public void setProgramList(List<ProgramState> programList) {
        programFrames.clear();
        programFrames=programList;
    }

    @Override
    public void add(ProgramState elem) {
        programFrames.add(elem);
    }

}
