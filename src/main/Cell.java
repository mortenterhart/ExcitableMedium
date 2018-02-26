package main;
import state.IState;

import java.util.ArrayList;

public class Cell {
    ArrayList<Cell> CellNeighbours;
    IState state;

    public Cell(IState state){
        this.state = state;
    }

    public int getCellState(){
        return state.getID();
    }

    public void findeNeighbour(){
        // die Frage ist wollen wir die Nachbarn als instanzen in der Liste Speicher oder doch nur die Koordinaten der Nachbarn?

    }


}
