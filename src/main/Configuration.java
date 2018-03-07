package main;


import random.MersenneTwister;

public enum Configuration {
    instance;

    public int gridSize = 21;
    public double fireProbability = 0.01;
    public int numberOfNeighbours = 4;
    public MersenneTwister random = new MersenneTwister();
}
