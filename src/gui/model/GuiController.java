package gui.model;

import config.Configuration;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import object.CellGrid;

public class GuiController {
    CellGrid gridwithCells;
    private int gridSize = Configuration.instance.gridSize;


    @FXML
    Button testButton;

    @FXML
    GridPane board;


    @FXML
    public void initialize() {
        javafx.scene.control.Label[] labels = new javafx.scene.control.Label[gridSize * gridSize];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new javafx.scene.control.Label();
            labels[i].setMinSize(50, 50);
            labels[i].setMaxSize(50, 50);
            labels[i].setPrefSize(50, 50);
            labels[i].setFont(new Font("Arial", 30));
            labels[i].setAlignment(Pos.CENTER);
            board.add(labels[i], i % gridSize, i / gridSize);
        }
    }

    void testButtonClicked() {
        testButton.setText("new Test");
    }

    public void setTestButtonText(String newText) {
        testButton.setText(newText);
    }

    public void setGridwithCells(CellGrid gridwithCells) {
        this.gridwithCells = gridwithCells;
    }

    public void updateGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                javafx.scene.control.Label label = (javafx.scene.control.Label) getNodeByRowColumnIndex(i, j, board);
                label.setText(gridwithCells.getCell(i, j).getCellState().getStateDescriptor().toString());

            }
        }
    }
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (node instanceof javafx.scene.control.Label) {
                if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                    result = node;
                    break;
                }
            }
        }
        return result;
    }
}
