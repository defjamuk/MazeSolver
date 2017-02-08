package com.camrade.mazesolver;

/**
 * Created by camrade on 05/02/17.
 */
public abstract class BaseSolver {

    // Use base classes
    protected R2D2 robot;
    protected Alderaan city;
    protected int xOrigin;
    protected int yOrigin;

    /*
    * Constructor to unwrap config and make
    * member available to derived classes
    * */
    public BaseSolver(SolverConfig config) {
        this.city = config.getCity();
        this.robot = config.getRobot();
        this.xOrigin = config.getxOrigin();
        this.yOrigin = config.getyOrigin();
    }
}
