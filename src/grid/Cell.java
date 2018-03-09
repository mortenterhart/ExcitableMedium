package grid;

import main.Configuration;
import state.IState;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private IState state;
    private List<Cell> neighbours;

    public Cell(IState state) {
        this.state = state;
        this.neighbours = new ArrayList<>(Configuration.instance.numberOfNeighbours);
    }

    public IState getCellState() {
        return state;
    }

    public List<Cell> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Cell cell) {
        neighbours.add(cell);
    }

    public void setState(IState state) {
        this.state = state;
    }

    public void updateCell() {
        state.change(this);
    }
}
