package main;

import config.WindowConfiguration;
import gui.controller.GuiController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import object.CellGrid;

public class ExcitableMedium implements Runnable {

    private CellGrid board;
    private GuiController controller;

    public ExcitableMedium(GuiController controller) {
        board = new CellGrid();
        this.controller = controller;
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
        board.print();
        while (board.hasExcitedCells()) {
            board.markCellStateModifications();
            board.dispatchCellMutations();
            board.print();


            Platform.runLater(new Task<Void>() {

                @Override
                protected Void call() {
                    controller.setCellBoard(board);

                    return null;
                }

            });

            waitInterval(WindowConfiguration.ITERATION_WAIT_INTERVAL);
        }
    }

    private void waitInterval(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exc) {
            exc.printStackTrace(System.err);
        }
    }
}
