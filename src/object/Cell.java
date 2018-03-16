package object;

import config.Configuration;
import state.ExcitedState;
import state.IState;

public class Cell {
    private IState state;
    private boolean stateWillBeMutated = false;
    private int refractoryCounter = 0;

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

    public void incrementRefractoryCounter() {
        refractoryCounter++;
    }

    public void resetRefractoryCounter() {
        refractoryCounter = 0;
    }

    public boolean hasFinishedRefractorinessPhase() {
        return refractoryCounter >= Configuration.instance.refractoryDuration - 1;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public boolean isExcited() {
        return state instanceof ExcitedState;
    }

    public void update() {
        if (stateWillBeMutated) {
            state.change(this);
        }

        stateWillBeMutated = false;
    }
}
