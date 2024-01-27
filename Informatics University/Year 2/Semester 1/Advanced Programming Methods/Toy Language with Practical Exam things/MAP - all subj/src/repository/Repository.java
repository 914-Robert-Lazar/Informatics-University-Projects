package repository;

import exceptions.FileException;
import exceptions.InterpreterException;
import model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements RepositoryInterface{
    private List<ProgramState> programStates;
    String logFilePath;

    public Repository() {
        this.programStates = new LinkedList<>() ;
    }

    public Repository(String filePath) throws FileException{
        try{
            // test if filePath is correct else throw exception
            PrintWriter testPath = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
        this.programStates = new LinkedList<>() ;
        this.logFilePath = filePath;
    }

    @Override
    public void addProgram(ProgramState state) {
        programStates.add(state);
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws InterpreterException {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        }catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
        logFile.println(state.toString());
        // If the stream has saved any characters from the various write() methods in a buffer, write them immediately
        // to their intended destination
        logFile.flush();
        if (state.getExeStack().isEmpty()) {
            logFile.close();
        }
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> list) {
        this.programStates =  list;
    }

    @Override
    public ProgramState getProgramStateWithId(int currentId) {
        for(ProgramState pr : this.programStates)
            if(pr.getId_thread() == currentId)
                return pr;
        return null;
    }
//    @Override
//    public ProgramState getCurrentProgram() {
//        ProgramState current = programStates.get(0);
//        programStates.removeFirst();
//        return current;
//    }
}
