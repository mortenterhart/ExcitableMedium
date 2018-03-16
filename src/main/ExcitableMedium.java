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
            System.out.println();
            if (started) {
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

    private void simulateDevelopment() {
        System.out.println("Simulation started");
        board.randomizeStartConstellation();
        updateGUIGridPane();

        while (board.hasExcitedCells() && started) {
            board.markCellStateModifications();
            board.dispatchCellMutations();
            waitIfSimulationHold();

            updateGUIGridPane();

            waitInterval(WindowConfiguration.ITERATION_WAIT_INTERVAL);
        }

        board.resetToQuiescent();
        updateGUIGridPane();
        System.out.println("Simulation ended");
        started = false;
    }

    private void updateGUIGridPane() {
        Platform.runLater(new Task<Void>() {
            /**
             * Invoked when the Task is executed, the call method must be overridden and
             * implemented by subclasses. The call method actually performs the
             * background thread logic. Only the updateProgress, updateMessage, updateValue and
             * updateTitle methods of Task may be called from code within this method.
             * Any other interaction with the Task from the background thread will result
             * in runtime exceptions.
             *
             * @return The result of the background work, if any.
             */
            @Override
            protected Void call() {
                controller.updateGrid();
                return null;
            }
        });
    }

    private void waitIfSimulationHold() {
        while (hold) {
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
