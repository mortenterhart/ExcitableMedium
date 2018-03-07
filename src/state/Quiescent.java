package state;

import main.Cell;

public class Quiescent implements IState {
    private int id = 0;


    @Override
    public void change(Cell cell) {
        //neighbor in fire -> cell in fire
//        for (Cell neighbour :cell.getNeighbours() ) {  //we can change it to an iterator
//            if (neighbour.getCellState() == 2)
//                cell.setState(new Excited()); //TODO Change -> if(instanceOf(neighbour.getState, Excited
//        }

        //if no neighbor in fire, do nothing

    }

    @Override
    public int getID() {
        return id;
    }
}
