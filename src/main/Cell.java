package main;

import state.IState;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private List<Cell> CellNeighbours;
    private IState state;
    private CellCoordinates coordinates;
    private List<Cell> neighbours;

    public Cell(IState state /*, CellCoordinates coordinates*/) {
        this.state = state;
        //this.coordinates = coordinates;
    }

    public int getCellState() {
        return state.getID();
    }

    public List<Cell> getNeighbours() {
        // die Frage ist, wollen wir die Nachbarn als Instanzen in der Liste speichern oder doch nur die Koordinaten der Nachbarn?
        return neighbours;
    }


    public void setState(IState state) {
        this.state = state;
    }

    public void updateCell() {
        state.change(this);
    }
}
