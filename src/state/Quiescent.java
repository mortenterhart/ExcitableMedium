package state;

import object.Cell;

public class Quiescent implements IState {
    private StateDescriptor descriptor = StateDescriptor.quiescent;

    public void change(Cell cell) {
        cell.setState(new Excited());
    }

    public StateDescriptor getStateDescriptor() {
        return descriptor;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof Quiescent;
    }

    @Override
    public String toString() {
        return (char) 27 + "[92mQ" + (char) 27 + "[0m";
    }
}
