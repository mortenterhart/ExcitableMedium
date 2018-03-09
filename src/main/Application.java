package main;

import grid.Grid;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private String[] arguments;

    public void startApplication() {
        Application.launch(arguments);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Grid grid = new Grid();
        grid.print();

        Pane rootElement = FXMLLoader.load(getClass().getResource(Configuration.instance.pathToFXMLFile));
        //primaryStage.show();

        //Hello and welcome to the ExcitableMedium Project.
        //My goals are to build a really easy application fast, it only should meets the minimum criteria of an ExcitableMedium.
        //That means, no dynamic grid Size, no selectable Start patterns

        //off cores such things can be added after the basic functionality is added. But at first we want a running Main in sense of
        //the agile principle: Working software is the primary measure of progress.

        for (int i = 0; i < 3; i++) {
            grid.updateCellStates();
            grid.print();
            System.out.println();
            System.out.println();
            grid.updateTime();
        }
    }

    public void setArguments(String ... arguments) {
        this.arguments = arguments;
    }
}
