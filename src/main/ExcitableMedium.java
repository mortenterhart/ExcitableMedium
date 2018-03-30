package main;

import gui.controller.GuiController;
import object.Cell;
import object.CellGrid;
import state.IState;

import java.util.ArrayList;
import java.util.List;

public class ExcitableMedium implements Runnable {

    private CellGrid board;
    private GuiController controller;
    private List<IState[][]> cellGridStates;
    private boolean initialRandomBoard = false;

    public ExcitableMedium(boolean initialRandomBoard) {
        board = new CellGrid();
        cellGridStates = new ArrayList<>();
        this.initialRandomBoard = initialRandomBoard;
    }

    public ExcitableMedium(GuiController controller) {
        this(true);
        this.controller = controller;
    }

    public ExcitableMedium(CellGrid board, boolean initialRandomBoard) {
        this(initialRandomBoard);
        this.board = board;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        simulateDevelopment();
    }

    private void simulateDevelopment() {
        if (initialRandomBoard) {
            board.randomizeStartConstellation();
        }
        encapsulateCellStates();

        while (board.containsExcitedOrRefractoryCells()) {
            board.markCellStateModifications();
            board.dispatchCellMutations();
            encapsulateCellStates();
        }

        dispatchStatesToController();
    }

    private void encapsulateCellStates() {
        Cell[][] cellStates = board.getCellBoard();
        IState[][] states = new IState[cellStates.length][cellStates[0].length];

        for (int x = 0; x < cellStates.length; x++) {
            for (int y = 0; y < cellStates[x].length; y++) {
                states[x][y] = cellStates[x][y].getCellState();
            }
        }

        cellGridStates.add(states);
    }

    private void dispatchStatesToController() {
        controller.setAlgorithmStates(cellGridStates);
    }

}
