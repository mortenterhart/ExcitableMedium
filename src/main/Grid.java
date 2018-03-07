package main;

import state.Excited;
import state.Quiescent;
import util.MersenneTwister;

import java.util.Random;

public class Grid {
    private Cell[][] grid;
    private int time;  //can later be changed to double


    private int gridSize = Configuration.instance.gridSize;
    private double fireProbability = Configuration.instance.fireProbibility;
    private MersenneTwister random = Configuration.instance.random;

    Grid() {
        this.time = 0;
        this.grid = gridBuilder();
    }

    private Cell[][] gridBuilder() {
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

    public void print() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j].getCellState());
            }
            System.out.println();
        }
    }

    public void calculateNeighbourCells() {

    }

    public void updateTime() {
        time++;
    }

    public void updateCellStates() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j].updateCell();
            }
            System.out.println();
        }
    }

}
