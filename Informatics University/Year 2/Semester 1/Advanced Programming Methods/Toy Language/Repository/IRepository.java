package Repository;

import java.io.IOException;
import java.util.List;

import Model.ProgramStateComponents.ProgramState;

public interface IRepository {
    void add(ProgramState programState);
    void logProgramState(ProgramState programState) throws IOException;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> programStates);
}
