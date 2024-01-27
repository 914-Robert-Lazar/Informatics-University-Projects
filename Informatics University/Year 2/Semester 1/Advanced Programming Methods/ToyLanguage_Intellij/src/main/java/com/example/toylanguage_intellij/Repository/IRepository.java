package com.example.toylanguage_intellij.Repository;

import java.io.IOException;
import java.util.List;

import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;

public interface IRepository {
    void add(ProgramState programState);
    void logProgramState(ProgramState programState) throws IOException;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> programStates);
}
