package state;

import object.Cell;

public class ExcitedState implements IState {
    private StateDescriptor descriptor = StateDescriptor.excited;

    public void change(Cell cell) {
        cell.setState(new RefractoryState());
    }

    public StateDescriptor getStateDescriptor(){
        return descriptor;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof ExcitedState;
    }

    @Override
    public String toString() {
        return (char) 27 + "[91mE" + (char) 27 + "[0m";
    }
}
