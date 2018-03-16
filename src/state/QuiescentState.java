package state;

import object.Cell;

public class QuiescentState implements IState {
    private StateDescriptor descriptor = StateDescriptor.quiescent;

    public void change(Cell cell) {
        cell.setState(new ExcitedState());
    }

    public StateDescriptor getStateDescriptor() {
        return descriptor;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof QuiescentState;
    }

    @Override
    public String toString() {
        return (char) 27 + "[92mQ" + (char) 27 + "[0m";
    }
}
