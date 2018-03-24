package gui.controller;

import config.Configuration;
import config.WindowConfiguration;
import gui.util.GraduallyIndexConverter;
import gui.util.GridPaneIterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import main.ExcitableMedium;
import state.IState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuiController {
    private int gridSize = Configuration.instance.gridSize;
    private GraduallyIndexConverter indexConverter;

    private List<IState[][]> algorithmStates;
    private GridDisplayDriver displayDriver;

    // GUI Configuration and Status Panes

    @FXML
    private Pane stateRevisionPane;

    @FXML
    private Label iterationLabel;

    // Configuration Slider

    @FXML
    private Slider speedSlider;

    @FXML
    private Slider excitabilitySlider;

    @FXML
    private Label excitabilityWarningLabel;

    @FXML
    private Button resetSpeedSlider;

    @FXML
    private Button resetFireSlider;

    // Simulation Matrix

    @FXML
    private GridPane gridPane;

    // GUI Control Buttons

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button holdButton;

    @FXML
    private Button previousStateButton;

    @FXML
    private Button nextStateButton;

    @FXML
    private void initialize() {
        algorithmStates = new ArrayList<>();
        indexConverter = new GraduallyIndexConverter(gridSize);

        initializeGUIActivation();
        buildRoundedCornersAroundStatePane();
        listenToSliderChanges();
        addColoredRectanglesToGrid();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(false);
        iterationLabel.setTextAlignment(TextAlignment.RIGHT);
    }

    private void initializeGUIActivation() {
        startButton.setDisable(false);
        stopButton.setDisable(true);
        holdButton.setDisable(true);
        previousStateButton.setDisable(true);
        nextStateButton.setDisable(true);

        stateRevisionPane.setDisable(true);
    }

    private void buildRoundedCornersAroundStatePane() {
        CornerRadii radii = new CornerRadii(10, 10, 10, 10, false);
        stateRevisionPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, radii, BorderWidths.DEFAULT)));
    }

    private void addColoredRectanglesToGrid() {
        int gridLength = (int) Math.pow(gridPane.getRowConstraints().size(), 2);
        for (int index = 0; index < gridLength; index++) {
            Rectangle colorLayer = new Rectangle(21, 21, Color.LIGHTGREEN);
            gridPane.add(colorLayer, indexConverter.convertToColumn(index), indexConverter.convertToRow(index));
            GridPane.setHalignment(colorLayer, HPos.CENTER);
            GridPane.setValignment(colorLayer, VPos.CENTER);
        }

    }

    private void listenToSliderChanges() {
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setExecutionSpeed(newValue.longValue());
            }
        });

        excitabilitySlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                excitabilityWarningLabel.setVisible(false);
                Configuration.instance.fireProbability = newValue.doubleValue() / 100.0;

                if (newValue.intValue() <= 0) {
                    excitabilityWarningLabel.setVisible(true);
                    excitabilityWarningLabel.setText("Warning: Probability is set to 0 percent!");
                }
            }
        });
    }

    private void setExecutionSpeed(long value) {
        WindowConfiguration.setIterationWaitInterval(1000L - value);
    }

    @FXML
    private void startSimulation() {
        instructDisplayDriver();

        activateComponentsOnStart();
    }

    private void instructDisplayDriver() {
        Thread algorithmThread = new Thread(new ExcitableMedium(this));
        algorithmThread.setDaemon(true);
        algorithmThread.start();
        waitForThread(algorithmThread);

        displayDriver = new GridDisplayDriver(gridPane, algorithmStates);
        displayDriver.setIterationLabel(iterationLabel);
        displayDriver.setStateButtons(previousStateButton, nextStateButton);
        Thread driverThread = new Thread(displayDriver);
        driverThread.setDaemon(true);
        driverThread.start();
    }

    private void waitForThread(Thread algorithmThread) {
        try {
            algorithmThread.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void stopSimulation() {
        displayDriver.stop();
        resetBoardToQuiescent();

        toggleControlButtonActivation();
        previousStateButton.setDisable(true);
        nextStateButton.setDisable(true);
    }

    @FXML
    private void holdSimulation() {
        displayDriver.toggleHold();
        stopButton.setDisable(!stopButton.disabledProperty().get());
    }

    @FXML
    private void moveToPreviousState() {
        displayDriver.returnToPreviousState();
        displayDriver.updateCurrentState();
    }

    @FXML
    private void moveToNextState() {
        displayDriver.proceedToNextState();
        displayDriver.updateCurrentState();
    }

    @FXML
    private void resetExecutionSpeed() {
        WindowConfiguration.setIterationWaitInterval(WindowConfiguration.DEFAULT_ITERATION_WAIT_INTERVAL);
        speedSlider.setValue(1000L - WindowConfiguration.DEFAULT_ITERATION_WAIT_INTERVAL);
    }

    @FXML
    private void resetExcitabilityProbability() {
        Configuration.instance.fireProbability = Configuration.instance.defaultFireProbability;
        excitabilitySlider.setValue(Configuration.instance.defaultFireProbability * 100.0);
    }

    private void activateComponentsOnStart() {
        stateRevisionPane.setDisable(false);
        toggleControlButtonActivation();
    }

    private void toggleControlButtonActivation() {
        startButton.setDisable(!startButton.disabledProperty().get());
        stopButton.setDisable(!stopButton.disabledProperty().get());
        holdButton.setDisable(!holdButton.disabledProperty().get());
    }

    private void resetBoardToQuiescent() {
        Iterator<Rectangle> gridIterator = new GridPaneIterator(gridPane);
        while (gridIterator.hasNext()) {
            gridIterator.next().setFill(Color.LIGHTGREEN);
        }
    }

    public void setAlgorithmStates(List<IState[][]> algorithmStates) {
        this.algorithmStates = algorithmStates;
    }
}
