package state;

import main.Cell;
import state.IState;

public class Excited implements IState {
    private int id = 2;


    @Override
    public void change(Cell cell) {
        //t+1 -> Refractory
        cell.setState(new Refractory());
    }

    public int getID(){
        return id;
    }
}
