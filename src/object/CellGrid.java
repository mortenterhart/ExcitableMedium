package object;

import config.Configuration;
import random.MersenneTwister;
import state.Excited;
import state.IState;
import state.Quiescent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CellGrid {
    private Cell[][] grid;

    private int gridSize = Configuration.instance.gridSize;
    private double fireProbability = Configuration.instance.fireProbability;
    private MersenneTwister random = Configuration.instance.mersenneTwister;

    public CellGrid() {
        randomizeStartConstellation();
    }

    public void randomizeStartConstellation() {
        grid = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (random.nextBoolean(fireProbability)) {
                    grid[i][j] = new Cell(new Excited());
                } else {
                    grid[i][j] = new Cell(new Quiescent());
                }
            }
        }
    }

    public void markCellStateModifications() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Cell currentCell = grid[x][y];
                List<IState> neighbourStates = collectNeighbourStates(x, y);

                switch (currentCell.getCellState().getStateDescriptor()) {
                    case quiescent:
                        if (anyNeighbourOnFire(neighbourStates)) {
                            currentCell.markAsMutable();
                        }
                        break;

                    case excited:
                    case refractory:
                        currentCell.markAsMutable();
                        break;
                }
            }
        }
    }

    private boolean anyNeighbourOnFire(List<IState> neighbourStates) {
        for (IState state : neighbourStates) {
            if (state instanceof Excited) {
                return true;
            }
        }
        return false;
    }

    private List<IState> collectNeighbourStates(int x, int y) {
        List<IState> states = new ArrayList<>(4);
        if (coordinatesInsideGrid(x - 1, y)) {
            states.add(grid[x - 1][y].getCellState());
        }

        if (coordinatesInsideGrid(x + 1, y)) {
            states.add(grid[x + 1][y].getCellState());
        }

        if (coordinatesInsideGrid(x, y - 1)) {
            states.add(grid[x][y - 1].getCellState());
        }

        if (coordinatesInsideGrid(x, y + 1)) {
            states.add(grid[x][y + 1].getCellState());
        }

        return states;
    }

    private boolean coordinatesInsideGrid(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    public void dispatchCellMutations() {
        Arrays.stream(grid).forEach(cells -> Arrays.stream(cells).forEach(
                cell -> {
                    if (cell.willStateBeMutated()) {
                        cell.update();
                    }
                }
        ));
    }

    public void resetToQuiescent() {
        Arrays.stream(grid).forEach(cells -> Arrays.stream(cells).forEach(
                cell -> cell.setState(new Quiescent())
        ));
    }

    public boolean hasExcitedCells() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Cell currentCell = grid[x][y];
                if (currentCell.isExcited()) {
                    return true;
                }
            }
        }
        
        return false;
    }

    public Cell[][] getCellBoard(){
        return grid;
    }
}
