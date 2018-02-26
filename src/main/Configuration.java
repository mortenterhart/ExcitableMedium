package main;

import util.MersenneTwister;
public enum  Configuration {
    instance;
    public int gridSize = 100;
    public double fireProbibility = 0.01;
    public MersenneTwister random = new MersenneTwister();

}
