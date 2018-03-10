package gui.controller;

import config.Configuration;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import object.Cell;
import object.CellGrid;

public class GuiController {
    private Cell[][] grid;
    private int gridSize = Configuration.instance.gridSize;

    @FXML
    private GridPane board;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button holdButton;

    public void initialize() {
        Label[] labels = new Label[gridSize * gridSize];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label();
            labels[i].setMinSize(50, 50);
            labels[i].setMaxSize(50, 50);
            labels[i].setPrefSize(50, 50);
            labels[i].setFont(new Font("Arial", 30));
            labels[i].setAlignment(Pos.CENTER);
            board.add(labels[i], i % gridSize, i / gridSize);
        }
    }

    public void updateGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Label label = (Label) getNodeByRowColumnIndex(i, j, board);
                label.setText(grid[i][j].getCellState().toString());

            }
        }
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (node instanceof Label) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                    result = node;
                    break;
                }
            }
        }
        return result;
    }

    public void testButtonClicked() {
        startButton.setText("new Test");
    }

    public void setTestButtonText(String newText) {
        startButton.setText(newText);
    }

    public void setCellBoard(CellGrid cellBoard) {
        this.grid = cellBoard.getCellBoard();
    }
}
