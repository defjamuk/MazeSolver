package com.camrade.mazesolver;

import becker.robots.*; // Import the robot's library.

/**
 * Created by camrade on 05/02/17.
 * Robot used to navigate the maze. Derived form the becker library base class
 */
public class R2D2 extends RobotSE {

    /*
    * Constructs a new Star Wars Bot --
    * */
    public R2D2(Alderaan city, int street, int avenue, Direction dir, int numThings)
    {
        super(city, street, avenue, dir, numThings); // Initializes the robot.
    }
}
