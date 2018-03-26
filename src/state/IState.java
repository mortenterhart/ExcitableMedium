package state;

import object.Cell;

public interface IState {
    void change(Cell cell);

    StateDescriptor getStateDescriptor();
}
