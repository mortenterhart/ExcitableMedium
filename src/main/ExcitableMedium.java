package main;

import config.WindowConfiguration;
import object.CellGrid;

import java.io.IOException;

public class ExcitableMedium implements Runnable {

    private CellGrid board;

    public ExcitableMedium() {
        board = new CellGrid();
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
        try {
            simulateDevelopment();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void simulateDevelopment() throws IOException {
        board.print();
        for (int i = 0; i < 1000; i++) {
            board.markCellStateModifications();
            board.dispatchCellMutations();
            board.print();

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
