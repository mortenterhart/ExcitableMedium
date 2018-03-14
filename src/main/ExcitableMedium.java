package main;

import config.WindowConfiguration;
import gui.controller.GuiController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import object.CellGrid;

public class ExcitableMedium implements Runnable {

    private CellGrid board;
    private GuiController controller;
    private boolean started = false;
    private boolean hold = false;

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
        controller.setAlgorithm(this);
        controller.setCellBoard(board.getCellBoard());

        while (true) {
            System.out.println("Thread is running, status: " + started);
            if (started) {
                System.out.println("Started");
                simulateDevelopment();
            }
        }
    }

    public void startSimulation() {
        started = true;
    }

    public void holdSimulation() {
        hold = !hold;
    }

    public void stopSimulation() {
        started = false;
    }

    public void simulateDevelopment() {
        while (board.hasExcitedCells() && started) {
            if (hold) {
                waitUntilHoldDisabled();
            }

            board.markCellStateModifications();
            board.dispatchCellMutations();

            Platform.runLater(new Task<Void>() {

                @Override
                protected Void call() {
                    controller.updateGrid();
                    return null;
                }

            });

            waitInterval(WindowConfiguration.ITERATION_WAIT_INTERVAL);
        }
        started = false;
    }

    private void waitUntilHoldDisabled() {
        while(hold);
    }

    private void waitInterval(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exc) {
            exc.printStackTrace(System.err);
        }
    }
}
