package main;

import config.Configuration;
import config.WindowConfiguration;
import gui.controller.GuiController;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Method;

public class Application extends javafx.application.Application {

    private FXMLLoader loader;
    private ExcitableMedium algorithm;

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
        primaryStage.setResizable(false);
        Pane rootElement = loader.load();

        primaryStage.setOnCloseRequest(event -> {
            algorithm.stopSimulation();
            primaryStage.close();
        });

        Scene guiScene = new Scene(rootElement, WindowConfiguration.WINDOW_WIDTH, WindowConfiguration.WINDOW_HEIGHT);
        primaryStage.setScene(guiScene);
        primaryStage.show();

        simulateExcitableMedium();
    }

    private void simulateExcitableMedium() {
        algorithm = new ExcitableMedium(loader.getController());
        Thread algorithmThread = new Thread(algorithm);
        algorithmThread.setDaemon(true);
        algorithmThread.start();
    }
}
