package gui.controller;

import config.Configuration;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.ExcitableMedium;
import object.Cell;
import state.StateDescriptor;

import java.util.Iterator;

public class GuiController {
    private Cell[][] cellBoard;
    private int gridSize = Configuration.instance.gridSize;
    private GraduallyIndexConverter indexConverter;
    private ExcitableMedium algorithm;

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
        indexConverter = new GraduallyIndexConverter(gridSize);

        int gridLength = (int) Math.pow(gridPane.getRowConstraints().size(), 2);
        for (int index = 0; index < gridLength; index++) {
            Rectangle colorLayer = new Rectangle(21, 21, Color.LIGHTGREEN);
            gridPane.add(colorLayer, index % gridSize, index / gridSize);
            GridPane.setHalignment(colorLayer, HPos.CENTER);
            GridPane.setValignment(colorLayer, VPos.CENTER);
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(true);
    }

    @FXML
    public void startSimulation(ActionEvent actionEvent) {
        algorithm.startSimulation();
    }

    @FXML
    public void stopSimulation(ActionEvent actionEvent) {
        algorithm.stopSimulation();
    }

    @FXML
    public void holdSimulation(ActionEvent actionEvent) {
        algorithm.holdSimulation();
    }

    public void updateGrid() {
        int childIndex = 0;
        Iterator<Rectangle> gridIterator = new GridPaneIterator(gridPane);
        while (gridIterator.hasNext()) {
            Rectangle currentField = gridIterator.next();
            int row = indexConverter.convertToRow(childIndex);
            int column = indexConverter.convertToColumn(childIndex);
            StateDescriptor updatedState = cellBoard[row][column].getCellState().getStateDescriptor();

            switch (updatedState) {
                case quiescent:
                    System.out.println("Cell " + row + ", " + column + " is quiescent");
                    currentField.setFill(Color.LIGHTGREEN);
                    break;
                case excited:
                    System.out.println("Cell " + row + ", " + column + " is excited");
                    currentField.setFill(Color.RED);
                    break;
                case refractory:
                    System.out.println("Cell " + row + ", " + column + " is refractory");
                    currentField.setFill(Color.YELLOW);
                    break;
                default:
                    System.out.println("No state");
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
}
