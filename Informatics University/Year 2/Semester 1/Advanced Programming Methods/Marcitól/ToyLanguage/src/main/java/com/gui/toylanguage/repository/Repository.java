package com.gui.toylanguage.repository;



import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.programState.PrgState;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository{
    List<PrgState> repo;
    String logFilePath;
    public Repository(String logfilePath) {
        this.repo = new LinkedList<PrgState>();
        this.logFilePath = logfilePath;
    }

    @Override
    public void add(PrgState e){
        repo.add(e);
    }

    @Override
    public List<PrgState> getPrgList() {
        return repo;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.repo = prgList;
    }

    @Override
    public synchronized void logPrgStateExec(PrgState prgState) throws MyException {
        PrintWriter logFile = null;
        try{
            logFile = new PrintWriter(new BufferedWriter(new FileWriter("./" + logFilePath, true)));
            logFile.print(prgState.toString());
        }
        catch (IOException e){
            throw new MyException(e.getMessage());
        }
        finally{
            if(logFile != null)
                logFile.close();
        }
    }

//    @Override
//    public PrgState getCrtPrg() {
//        return repo.get(0);
//    }

    @Override
    public String toString() {
        return "Repository{" +
                "repo=" + repo +
                '}';
    }
}
