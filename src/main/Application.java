package main;

public class Application {

    public static void main(String [] args){
        Grid grid = new Grid();
        System.out.println(grid);
        grid.print();

        //Hello and welcome to the ExcitableMedium Project.
        //My goals are to build a really easy application fast, it only should meets the minimum criteria of an ExcitableMedium.
        //That means, no dynamic grid Size, no selectable Start patterns

        //off cores such things can be added after the basic functionality is added. But at first we want a running Application in sense of
        //the agile principle: Working software is the primary measure of progress.

        while (true){
            grid.updateCellStates();
            grid.print();
            System.out.println();
            System.out.println();
            grid.updateTime();
        }
    }
}
