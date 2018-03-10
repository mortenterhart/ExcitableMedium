package gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import object.CellGrid;

public class GuiController {
    private CellGrid cellBoard;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button holdButton;

    @FXML
    public void testButtonClicked() {
        startButton.setText("new Test");
    }

    public void setTestButtonText(String newText) {
        startButton.setText(newText);
    }

    public void setCellBoard(CellGrid cellBoard) {
        this.cellBoard = cellBoard;
    }
}
