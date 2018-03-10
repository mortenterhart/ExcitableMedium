package object;

import state.IState;

public class Cell {
    private IState state;
    private boolean stateWillBeMutated = false;

    public Cell(IState state) {
        this.state = state;
    }

    public IState getCellState() {
        return state;
    }

    public void markAsMutable() {
        stateWillBeMutated = true;
    }

    public boolean willStateBeMutated() {
        return stateWillBeMutated;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public void update() {
        if (stateWillBeMutated) {
            state.change(this);
        }

        stateWillBeMutated = false;
    }
}
