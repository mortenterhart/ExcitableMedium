package state;

import grid.Cell;

public interface IState {
    void change(Cell cell);
    int getID();
}
