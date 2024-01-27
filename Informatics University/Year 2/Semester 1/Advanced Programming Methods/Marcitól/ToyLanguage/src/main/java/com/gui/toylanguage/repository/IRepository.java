package com.gui.toylanguage.repository;

import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.programState.PrgState;

import java.util.List;

public interface IRepository {
    void add(PrgState e);
    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> prgList);
    void logPrgStateExec(PrgState prgState) throws MyException;
}
