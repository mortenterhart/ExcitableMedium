package state;

import object.Cell;

public class RefractoryState implements IState {
    private StateDescriptor descriptor = StateDescriptor.refractory;

    public void change(Cell cell) {
        cell.setState(new QuiescentState());
    }

    public StateDescriptor getStateDescriptor() {
        return descriptor;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof RefractoryState;
    }

    @Override
    public String toString() {
        return (char) 27 + "[93mR" + (char) 27 + "[0m";
    }
}
