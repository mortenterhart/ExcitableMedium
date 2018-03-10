package main;

import config.Configuration;
import config.WindowConfiguration;
import gui.controller.GuiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private FXMLLoader loader;

    public Application() {
        loader = new FXMLLoader(this.getClass().getResource(Configuration.instance.fxmlPackagePath));
    }

    public void startApplication(String... arguments) {
        launch(arguments);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        primaryStage.setMaxHeight(WindowConfiguration.WINDOW_HEIGHT);
        primaryStage.setMaxWidth(WindowConfiguration.WINDOW_WIDTH);
        Pane rootElement = loader.load();

        primaryStage.setOnCloseRequest(event -> primaryStage.close());

        Scene guiScene = new Scene(rootElement, WindowConfiguration.WINDOW_WIDTH, WindowConfiguration.WINDOW_HEIGHT);
        primaryStage.setScene(guiScene);
        primaryStage.show();

        simulateExcitableMedium();
    }

    private void simulateExcitableMedium() {
        Thread algorithmThread = new Thread(new ExcitableMedium());
        algorithmThread.setDaemon(true);
        algorithmThread.start();
    }

    public GuiController getController() {
        return loader.getController();
    }
}
