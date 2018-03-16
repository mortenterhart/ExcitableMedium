package config;


import random.MersenneTwister;

public enum Configuration {
    instance;

    public final String fxmlPackagePath = "/gui/model/GridVisualization.fxml";

    public int gridSize = 21;
    public double fireProbability = 0.01;
    public int numberOfNeighbours = 4;
    public int refractoryDuration = 2;
    public MersenneTwister mersenneTwister = new MersenneTwister();
}
