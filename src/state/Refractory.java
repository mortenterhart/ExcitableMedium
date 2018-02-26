package state;

import main.Cell;
import state.IState;

public class Refractory implements IState {
    int id = 1;


    @Override
    public void change(Cell cell) {
        //t+1 -> Quiescent
        cell.setState(new Quiescent());

    }

    @Override
    public int getID() {
        return id;
    }
}
