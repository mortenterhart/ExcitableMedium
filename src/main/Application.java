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
    private String[] args;

    public Application() {
        loader = new FXMLLoader(this.getClass().getResource(Configuration.instance.fxmlPackagePath));
    }

    public void startApplication(String... arguments) {
        this.args = arguments;
        launch(arguments);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        primaryStage.setMaxHeight(WindowConfiguration.WINDOW_HEIGHT);
        primaryStage.setMaxWidth(WindowConfiguration.WINDOW_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.setTitle(WindowConfiguration.TITLE);
        Pane rootElement = loader.load();
        GuiController controller = loader.getController();

        controller.setArguments(args);
        primaryStage.setOnCloseRequest(event -> primaryStage.close());

        Scene guiScene = new Scene(rootElement);
        primaryStage.setScene(guiScene);
        primaryStage.show();
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
