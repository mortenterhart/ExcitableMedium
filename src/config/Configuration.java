package config;


import random.MersenneTwister;

public enum Configuration {
    instance;

    public final String fxmlPackagePath = "/gui/model/GridVisualization.fxml";
    public final String paneCSSPackagePath = "/gui/model/paneRoundBorders.css";

    public final int gridSize = 21;
    public final double defaultFireProbability = 0.05;
    public double fireProbability = 0.05;
    public int numberOfNeighbours = 4;
    public int refractoryDuration = 2;
    public MersenneTwister mersenneTwister = new MersenneTwister();

}
