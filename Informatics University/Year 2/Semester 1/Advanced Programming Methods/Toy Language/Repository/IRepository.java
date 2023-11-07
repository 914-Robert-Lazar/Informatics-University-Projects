package Repository;

import Model.ProgramStateComponents.ProgramState;

public interface IRepository {
    ProgramState getCurrentProgram();
    void add(ProgramState programState);
}
