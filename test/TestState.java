import main.ExcitableMedium;
import object.Cell;
import object.CellGrid;
import org.junit.Before;
import org.junit.Test;
import state.ExcitedState;
import state.QuiescentState;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestState {
    CellGrid cellGrid;
    Cell exictedCell;
    ExcitableMedium excitableMedium;
    private Cell[][] grid;
    @Before
    public void initialize() {
        exictedCell = new Cell(new ExcitedState());
        grid = new Cell[2][2];
        grid[0][0] = exictedCell;
        cellGrid = new CellGrid(grid);
        excitableMedium = new ExcitableMedium(cellGrid);

    }

    @Test
    public void testBurningCell(){
        excitableMedium.run();
        assertThat(cellGrid.getCellBoard()[0][0].getCellState(), instanceOf(QuiescentState.class));

    }
}
