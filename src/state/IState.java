package state;

import main.Cell;

public interface IState {
    void change(Cell cell);
    int getID();
}
