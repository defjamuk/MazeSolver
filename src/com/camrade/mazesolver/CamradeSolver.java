package com.camrade.mazesolver;

import becker.robots.Direction;


/**
 * Created by jazz- on 05/02/2017.
 */
public class CamradeSolver extends BaseSolver implements ISolver {

    public CamradeSolver(SolverConfig config) {
        super(config);
    }

    @Override
    public void RunSolver() {

        robot.turnLeft();
        robot.move();

    }
}

