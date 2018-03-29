package object;

import config.Configuration;
import random.MersenneTwister;
import state.ExcitedState;
import state.IState;
import state.QuiescentState;
import state.StateDescriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CellGrid {
    private Cell[][] cellGrid;

    private int gridSize = Configuration.instance.gridSize;
    private double fireProbability = Configuration.instance.fireProbability;
    private MersenneTwister random = Configuration.instance.mersenneTwister;

    public CellGrid() {
        cellGrid = new Cell[gridSize][gridSize];
    }

    public void fillWith(StateDescriptor state) {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                cellGrid[x][y] = new Cell(state.getInstance());
            }
        }
    }

    public void randomizeStartConstellation() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (random.nextBoolean(fireProbability)) {
                    cellGrid[x][y] = new Cell(new ExcitedState());
                } else {
                    cellGrid[x][y] = new Cell(new QuiescentState());
                }
            }
        }
    }

    public void markCellStateModifications() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Cell currentCell = cellGrid[x][y];
                List<IState> neighbourStates = collectNeighbourStates(y, x);

                switch (currentCell.getCellState().getStateDescriptor()) {
                    case quiescent:
                        if (anyNeighbourOnFire(neighbourStates)) {
                            currentCell.markAsMutable();
                        }
                        break;

                    case excited:
                        currentCell.markAsMutable();
                        break;
                    case refractory:
                        if (currentCell.hasFinishedRefractorinessPhase()) {
                            currentCell.markAsMutable();
                            currentCell.resetRefractoryCounter();
                        } else {
                            currentCell.incrementRefractoryCounter();
                        }
                        break;
                }
            }
        }
    }

    private boolean anyNeighbourOnFire(List<IState> neighbourStates) {
        for (IState state : neighbourStates) {
            if (state instanceof ExcitedState) {
                return true;
            }
        }
        return false;
    }

    private List<IState> collectNeighbourStates(int x, int y) {
        List<IState> states = new ArrayList<>(4);
        if (coordinatesInsideGrid(x - 1, y)) {
            states.add(cellGrid[x - 1][y].getCellState());
        }

        if (coordinatesInsideGrid(x + 1, y)) {
            states.add(cellGrid[x + 1][y].getCellState());
        }

        if (coordinatesInsideGrid(x, y - 1)) {
            states.add(cellGrid[x][y - 1].getCellState());
        }

        if (coordinatesInsideGrid(x, y + 1)) {
            states.add(cellGrid[x][y + 1].getCellState());
        }

        return states;
    }

    private boolean coordinatesInsideGrid(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    public Cell get(int x, int y) {
        checkBoundaries(x, y);
        return cellGrid[x][y];
    }

    private void checkBoundaries(int x, int y) {
        if (x < 0 || x >= gridSize || y < 0 || y >= gridSize) {
            throw new IndexOutOfBoundsException("coordinates [" + x + ", " + y + "] do not comply to cellGrid " +
                    "boundaries of size " + gridSize);
        }
    }

    public void dispatchCellMutations() {
        Arrays.stream(cellGrid).forEach(cells -> Arrays.stream(cells).forEach(
                cell -> {
                    if (cell.willStateBeMutated()) {
                        cell.update();
                    }
                }
        ));
    }

    public void resetToQuiescent() {
        Arrays.stream(cellGrid).forEach(cells -> Arrays.stream(cells).forEach(
                cell -> cell.setState(new QuiescentState())
        ));
    }

    public boolean containsExcitedCells() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Cell currentCell = cellGrid[x][y];
                if (currentCell.isExcited()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean containsExcitedOrRefractoryCells() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Cell currentCell = cellGrid[x][y];
                if (currentCell.isExcited() || currentCell.isRefractory()) {
                    return true;
                }
            }
        }

        return false;
    }

    public Cell[][] getCellBoard() {
        return cellGrid;
    }
}
