package state;

import object.Cell;

public class Excited implements IState {
    private StateDescriptor descriptor = StateDescriptor.excited;

    public void change(Cell cell) {
        cell.setState(new Refractory());
    }

    public StateDescriptor getStateDescriptor(){
        return descriptor;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof Excited;
    }

    @Override
    public String toString() {
        return (char) 27 + "[91mE" + (char) 27 + "[0m";
    }
}
