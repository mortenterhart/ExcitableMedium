package state;

import main.Cell;

public class Quiescent implements IState {
    private int id = 0;

    @Override
    public void change(Cell cell) {
        //neighbor in fire -> cell in fire
        for (Cell neighbour : cell.getNeighbours() ) {
            if (neighbour.getCellState() instanceof Excited) {
                cell.setState(new Excited());
            }
        }

        //if no neighbor in fire, do nothing

    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Q";
    }
}
