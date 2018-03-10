package gui.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import object.CellGrid;

public class GuiController {
    CellGrid gridwithCells;

    @FXML Button testButton;

    @FXML
    void testButtonClicked(){
        testButton.setText("new Test");
    }

    public void setTestButtonText(String newText){
        testButton.setText(newText);
    }

    public void setGridwithCells(CellGrid gridwithCells) {
        this.gridwithCells = gridwithCells;
    }
}
