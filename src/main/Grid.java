package main;

import random.MersenneTwister;
import state.Excited;
import state.Quiescent;

public class Grid {
    private Cell[][] grid;
    private int time;

    private int gridSize = Configuration.instance.gridSize;
    private double fireProbability = Configuration.instance.fireProbability;
    private MersenneTwister random = Configuration.instance.random;

    public Grid() {
        this.time = 0;
        this.grid = buildGrid();
        calculateNeighbourCells();
    }

    private Cell[][] buildGrid() {
        Cell[][] cells = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (random.nextBoolean(fireProbability)) {
                    cells[i][j] = new Cell(new Excited());
                } else {
                    cells[i][j] = new Cell(new Quiescent());
                }
            }
        }
        return cells;
    }

    private void calculateNeighbourCells() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Cell current = grid[x][y];
                if (areCoordinatesDefined(x - 1, y)) {
                    current.addNeighbour(grid[x - 1][y]);
                }

                if (areCoordinatesDefined(x + 1, y)) {
                    current.addNeighbour(grid[x + 1][y]);
                }

                if (areCoordinatesDefined(x, y - 1)) {
                    current.addNeighbour(grid[x][y - 1]);
                }

                if (areCoordinatesDefined(x, y + 1)) {
                    current.addNeighbour(grid[x][y + 1]);
                }
            }
        }
    }

    private boolean areCoordinatesDefined(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    public void print() {
        for (int i = 0; i < gridSize; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < gridSize; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j].getCellState() + " ");
            }
            System.out.println();
        }
    }

    public void updateTime() {
        time++;
    }

    public void updateCellStates() {
        Cell[][] originalCells = grid.clone();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                originalCells[i][j].updateCell();
            }
            System.out.println();
        }
    }

}
