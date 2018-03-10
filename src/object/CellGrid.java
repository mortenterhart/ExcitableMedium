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
        buildGrid();
    }

    private void buildGrid() {
        grid = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                //if (random.nextBoolean(fireProbability)) {
                //grid[i][j] = new Cell(new Excited());
                //} else {
                grid[i][j] = new Cell(new Quiescent());
                //}
            }
        }

        grid[0][0] = new Cell(new Excited());
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

    public void dispatchCellMutations() {
        Arrays.stream(grid).forEach(cells -> Arrays.stream(cells).forEach(
                cell -> {
                    if (cell.willStateBeMutated()) {
                        cell.update();
                    }
                }
        ));
    }

    private boolean coordinatesInsideGrid(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    public void print() {
        System.out.print("  ");
        for (int i = 0; i < gridSize; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
        for (int i = 0; i < gridSize; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j].getCellState() + "  ");
            }
            System.out.println();
        }
    }
}
