package repository;

import exception.InterpreterException;

import java.io.IOException;
import java.util.List;

public interface RepositoryInterface<T> {
    T getCurrentProgram() throws InterpreterException;
    void logProgramStateExecution(T state) throws InterpreterException;
    List<T> getProgramList();
    void setProgramList(List<T> programList);
    void add(T elem);
}
