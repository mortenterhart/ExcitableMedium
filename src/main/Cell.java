package main;
import state.IState;

import java.util.ArrayList;

public class Cell {
    ArrayList<Cell> CellNeighbours;
    IState state;
    CellCoordinates coordinates;
    ArrayList<Cell> neighbours;

    public Cell(IState state/*, CellCoordinates coordinates*/){
        this.state = state;
        //this.coordinates = coordinates;
    }

    public int getCellState(){
        return state.getID();
    }

    public void findeNeighbour(){
        // die Frage ist wollen wir die Nachbarn als instanzen in der Liste Speicher oder doch nur die Koordinaten der Nachbarn?

    }


}