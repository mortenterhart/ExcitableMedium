package gui.controller;

import config.Configuration;
import config.WindowConfiguration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.ExcitableMedium;
import object.Cell;
import object.CellGrid;
import state.IState;
import state.StateDescriptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuiController {
    private Cell[][] cellBoard;
    private List<IState[][]> algorithmStates;
    private int gridSize = Configuration.instance.gridSize;
    private GraduallyIndexConverter indexConverter;
    private ExcitableMedium algorithm;

    @FXML
    private Slider speedSlider;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button holdButton;

    @FXML
    public void initialize() {
        algorithmStates = new ArrayList<>();
        indexConverter = new GraduallyIndexConverter(gridSize);
        startButton.setDisable(false);
        stopButton.setDisable(true);
        holdButton.setDisable(true);

        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setExecutionSpeed(newValue.longValue());
            }
        });

        int gridLength = (int) Math.pow(gridPane.getRowConstraints().size(), 2);
        for (int index = 0; index < gridLength; index++) {
            Rectangle colorLayer = new Rectangle(21, 21, Color.LIGHTGREEN);
            gridPane.add(colorLayer, index % gridSize, index / gridSize);
            GridPane.setHalignment(colorLayer, HPos.CENTER);
            GridPane.setValignment(colorLayer, VPos.CENTER);
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(false);
    }

    private void setExecutionSpeed(long value) {
        WindowConfiguration.setIterationWaitInterval(1000L - value);
    }

    @FXML
    public void startSimulation() {
        algorithm.startSimulation();
        toggleButtonActivation();
    }

    @FXML
    public void stopSimulation() {
        algorithm.stopSimulation();
        toggleButtonActivation();
    }

    @FXML
    public void holdSimulation() {
        algorithm.holdSimulation();
        stopButton.setDisable(!stopButton.disabledProperty().get());
    }

    private void toggleButtonActivation() {
        startButton.setDisable(!startButton.disabledProperty().get());
        stopButton.setDisable(!stopButton.disabledProperty().get());
        holdButton.setDisable(!holdButton.disabledProperty().get());
    }

    public void updateGrid() {
        int childIndex = 0;
        Iterator<Rectangle> gridIterator = new GridPaneIterator(gridPane);
        while (gridIterator.hasNext()) {
            System.out.println("gridIterator has next cell");
            Rectangle currentField = gridIterator.next();
            System.out.println("Calculating currentField");
            int row = indexConverter.convertToRow(childIndex);
            System.out.println("Calculating row");
            int column = indexConverter.convertToColumn(childIndex);
            System.out.println("Calculating column");
            StateDescriptor updatedState = cellBoard[row][column].getCellState().getStateDescriptor();
            System.out.println("cell is in state " + updatedState.name());

            switch (updatedState) {
                case quiescent:
                    System.out.println("cell is quiescent");
                    currentField.setFill(Color.LIGHTGREEN);
                    break;
                case excited:
                    System.out.println("cell is excited");
                    currentField.setFill(Color.RED);
                    break;
                case refractory:
                    System.out.println("cell is refractory");
                    currentField.setFill(Color.YELLOW);
                    break;
                default:
                    throw new IllegalStateException("state " + updatedState.name() + " is undefined");
            }
            childIndex++;
        }
    }

    public void setCellBoard(Cell[][] cellBoard) {
        this.cellBoard = cellBoard;
    }

    public void setAlgorithm(ExcitableMedium algorithm) {
        this.algorithm = algorithm;
    }

    public void setAlgorithmStates(List<IState[][]> algorithmStates) {
        this.algorithmStates = algorithmStates;
    }
}
