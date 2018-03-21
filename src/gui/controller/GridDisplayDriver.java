package gui.controller;

import config.Configuration;
import config.WindowConfiguration;
import gui.util.GraduallyIndexConverter;
import gui.util.GridPaneIterator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import state.IState;
import state.StateDescriptor;

import java.util.Iterator;
import java.util.List;

public class GridDisplayDriver implements Runnable {
    private GridPane gridPane;
    private GraduallyIndexConverter indexConverter;
    private List<IState[][]> gridStates;
    private boolean started = true;
    private int stateIndex = 0;
    private boolean hold = false;

    private Label iterationLabel;

    public GridDisplayDriver(GridPane gridPane, List<IState[][]> cellBoardStates) {
        this.gridPane = gridPane;
        this.gridStates = cellBoardStates;
        this.indexConverter = new GraduallyIndexConverter(Configuration.instance.gridSize);
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
        while (started && stateIndex < gridStates.size()) {
            updateSimulationStateComponents();
            holdIfTriggered();
            waitInterval(WindowConfiguration.ITERATION_WAIT_INTERVAL);
            stateIndex++;
        }
    }

    public void returnToPreviousState() {
        stateIndex--;
    }

    public void proceedToNextState() {
        stateIndex++;
    }

    public void stop() {
        started = false;
    }

    public void toggleHold() {
        hold = !hold;
    }

    private void holdIfTriggered() {
        while (hold) {
            waitInterval(WindowConfiguration.ITERATION_WAIT_INTERVAL);
        }
    }

    private void updateSimulationStateComponents() {
        displayGridStates(gridStates.get(stateIndex));
        displayIterationNumber();
    }

    public void updateCurrentState() {
        displayGridStates(gridStates.get(stateIndex));
        displayIterationNumber();
    }

    private void displayGridStates(IState[][] currentState) {
        int childIndex = 0;
        Iterator<Rectangle> gridIterator = new GridPaneIterator(gridPane);
        while (gridIterator.hasNext()) {
            Rectangle currentField = gridIterator.next();
            int row = indexConverter.convertToRow(childIndex);
            int column = indexConverter.convertToColumn(childIndex);

            StateDescriptor updatedState = currentState[row][column].getStateDescriptor();
            switch (updatedState) {
                case quiescent:
                    currentField.setFill(Color.LIGHTGREEN);
                    break;
                case excited:
                    currentField.setFill(Color.RED);
                    break;
                case refractory:
                    currentField.setFill(Color.YELLOW);
                    break;
                default:
                    throw new IllegalStateException("state " + updatedState.name() + " is undefined");
            }
            childIndex++;
        }
    }

    private void displayIterationNumber() {
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
             * @throws Exception an unhandled exception which occurred during the
             *                   background operation
             */
            protected Void call() throws Exception {
                iterationLabel.setText(String.format("%d/%d", stateIndex + 1, gridStates.size()));
                return null;
            }
        });
    }

    public boolean isAtFirstState() {
        return stateIndex <= 0;
    }

    public boolean isAtLastState() {
        return stateIndex >= gridStates.size() - 1;
    }

    private void waitInterval(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exc) {
            exc.printStackTrace(System.err);
        }
    }

    public void setIterationLabel(Label iterationLabel) {
        this.iterationLabel = iterationLabel;
    }
}
