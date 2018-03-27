import gui.controller.GuiController;
import main.ExcitableMedium;
import main.Main;
import object.Cell;
import object.CellGrid;
import org.hamcrest.core.IsInstanceOf;
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


    }

    @Test
    public void testInit() {
        assertNotNull(grid);
        assertNotNull(excitableMedium);
        assertNotNull(excitedCell);
    }

    @Test
    public void testStuff(){
        cellGrid.markCellStateModifications();
        QuiescentState quiescentState = new QuiescentState();
        assertThat(quiescentState, instanceOf(grid[0][0].getCellState().getClass()));
    }



}
