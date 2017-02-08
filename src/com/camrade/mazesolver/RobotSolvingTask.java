package com.camrade.mazesolver;

/**
 * Created by Camrade on 05/02/17.
 * Main class for te Robot Task
 */
public class RobotSolvingTask{

    private ISolver solver;

    /*
    * Constructor
    * */
    public RobotSolvingTask(ISolver solver) {
        this.solver = solver;
    }

    /*
    * Performs the solving of the problem
    * using the passed in solving algorithm
    * */
    public void PerformSolve(){
        this.solver.RunSolver();
    }
}
