import gui.controller.GuiController;
import main.ExcitableMedium;
import main.Main;
import object.Cell;
import object.CellGrid;
import org.junit.Before;
import org.junit.Test;
import state.ExcitedState;
import state.QuiescentState;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestState {
    private CellGrid cellGrid;
    private Cell excitedCell;
    private ExcitableMedium excitableMedium;
    private Cell[][] grid;
    private Main main;
    private GuiController controller;

    @Before
    public void initialize() {
        excitedCell = new Cell(new ExcitedState());
        grid = new Cell[1][1];
        grid[0][0] = excitedCell;
        cellGrid = new CellGrid(grid);
        excitableMedium = new ExcitableMedium(cellGrid, false);

        main = new Main();
        main.main(new String[0]);


    }

    @Test
    public void testInit() {
        assertNotNull(grid);
        assertNotNull(excitableMedium);
        assertNotNull(excitedCell);
        assertNotNull(main);
        assertNotNull(main.getApplication());


        assertNotNull(main.getApplication().getLoader());

        controller = (GuiController) main.getApplication().getLoader().getController();
        assertNotNull(controller);


        assertNotNull(controller);
        assertNotNull(controller.getAlgorithmStates());
        assertNotNull(controller.getDisplayDriver());
        assertNotNull(controller.getExcitabilitySlider());
        assertNotNull(controller.getExcitabilityWarningLabel());
        assertNotNull(controller.getGridPane());
        assertNotNull(controller.getHoldButton());
        assertNotNull(controller.getIndexConverter());
        assertNotNull(controller.getIterationLabel());
        assertNotNull(controller.getNextStateButton());
        assertNotNull(controller.getPreviousStateButton());
        assertNotNull(controller.getResetFireSlider());
        assertNotNull(controller.getResetSpeedSlider());
        assertNotNull(controller.getSpeedSlider());


    }



}
