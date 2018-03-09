package main;


import random.MersenneTwister;

public enum Configuration {
    instance;

    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String pathToFXMLFile = userDirectory + fileSeparator + "GridVisualization.fxml";

    public int gridSize = 21;
    public double fireProbability = 0.01;
    public int numberOfNeighbours = 4;
    public MersenneTwister mersenneTwister = new MersenneTwister();
}
