package state;

import object.Cell;

public class Refractory implements IState {
    private StateDescriptor descriptor = StateDescriptor.refractory;

    public void change(Cell cell) {
        cell.setState(new Quiescent());
    }

    public StateDescriptor getStateDescriptor() {
        return descriptor;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof Refractory;
    }

    @Override
    public String toString() {
        return (char) 27 + "[93mR" + (char) 27 + "[0m";
    }
}
