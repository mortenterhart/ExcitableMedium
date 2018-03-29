import main.ExcitableMedium;
import object.CellGrid;
import org.junit.Before;
import org.junit.Test;
import state.ExcitedState;
import state.RefractoryState;
import state.StateDescriptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IStateTest {
    private CellGrid cellGrid;
    private ExcitableMedium excitableMedium;

    @Before
    public void initialize() {
        cellGrid = new CellGrid();
        cellGrid.fillWith(StateDescriptor.quiescent);
        cellGrid.get(0, 0).setState(new ExcitedState());
        excitableMedium = new ExcitableMedium(cellGrid, false);
    }

    @Test
    public void testInit() {
        assertNotNull(cellGrid);
        assertNotNull(excitableMedium);
        assertNotNull(cellGrid.get(0, 0));
    }

    @Test
    public void testCellDevelopment() {
        cellGrid.markCellStateModifications();
        cellGrid.dispatchCellMutations();
        assertTrue(cellGrid.get(0, 0).getCellState() instanceof RefractoryState);
    }
}
