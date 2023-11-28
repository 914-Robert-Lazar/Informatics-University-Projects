package Repository;

import java.io.IOException;

import Model.ProgramStateComponents.ProgramState;

public interface IRepository {
    ProgramState getCurrentProgram();
    void add(ProgramState programState);
    void logProgramState() throws IOException;
    void removeCurrentProgram();
}
