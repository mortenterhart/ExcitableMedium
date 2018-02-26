package main;

import state.Excited;
import state.Quiescent;

import java.util.Random;

public class Grid {
    private Cell[][] grid;
    private int gridSize = Configuration.instance.gridSize;
    private double fireProbability = Configuration.instance.fireProbibility;
    private Random random;

    Grid() {
        this.grid = gridBuilder();
    }

    private Cell[][] gridBuilder() {
        Cell[][] cells = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize ; i++) {
            for (int j = 0; j < gridSize ; j++) {
                if(Math.random() < fireProbability){  //TODO Change to the beloved Mersenee Twister
                cells[i][j] = new Cell(new Excited());
                }else {
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

    public void calculateNeighbourCells(){

    }

}
