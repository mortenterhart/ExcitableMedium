package main;

import state.IState;

public class Cell {
    IState state;

    public Cell(IState state){
        this.state = state;
    }

    public int getCellState(){
        return state.getID();
    }


}
