public class Cell {
    IState state;

    public Cell(IState state){
        this.state = state.setSate(new Quiescent());
    }
}
