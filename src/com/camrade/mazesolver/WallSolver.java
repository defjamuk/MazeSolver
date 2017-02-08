package com.camrade.mazesolver;

import becker.robots.Direction;

/**
 * Created by camrade on 05/02/17.
 * This class implements a well known maze solving algorithm called
 * "Walk The Wall". This implementation walks the left wall until
 * the maze is solved.
 */
public class WallSolver extends BaseSolver implements ISolver  {

    // Private constructor
    WallSolver(SolverConfig config) {
        super(config);
    }

    @Override
    public void RunSolver() {
        RunWallSolverMethod();
    }

    private void RunWallSolverMethod()
    {
        findWall(); // Use the method created to have the robot find the first wall in the maze from its starting position.
        walkMaze(); // Have the robot navigate around the maze to the origin by following the left walls, using the method created in the child class.
    }

    /**
     * move will override the move method inherited by the RobotSE class to only move if the front is clear and if the robot is not at the origin.
     * @return Nothing.
     */
    private void move()
    {
        if (robot.frontIsClear() && (robot.getAvenue() != 0 || robot.getStreet() != 0)) // Checks to see if there is no wall in front of the robot and if the robot is not at the origin position.
        {
            robot.move(); // If there is no wall and the robot is not at the origin, move the robot one block forward using the inherited move method.
        }
    }

    /**
    * Turn Right
    */
    private void turnRight(){
        robot.turnRight();
    }

    /**
    * Turn left
    */
    private void turnLeft(){
        robot.turnLeft();
    }

    /**
     * turnBack turns the robot around
     */
    private void turnBack()
    {
        robot.turnAround();
    }

    /**
     * isNextToLeftWall will return a boolean value that will represent whether
     * or not there is a wall to the left of the robot by turning left, checking
     * if there is a wall there, and then turning back right.
     */
    private boolean isNextToLeftWall()
    {
        boolean leftWall = true; // Assumes true

        turnLeft(); // Turn turn left to start the checks.

        if (robot.frontIsClear()) // Checks to see if there is no wall in front of the robot.
            leftWall = false; // Looks Front it clear so no left wall.

        turnRight(); // Turn the robot right, back to its original direction.

        return leftWall; // Return the value of the boolean that represents whether or not there is a wall to the left of the robot.
    }

    /**
     * turnSouth will check which direction the robot is facing, and use if structures to turn
     * the robot appropriately to face South, depending on which way it is already facing.
     */

    private void turnSouth()
    {
        Direction dir = robot.getDirection(); // Represents a Direction variable called dir that will get the current direction the robot is facing.

        if (dir == Direction.WEST) // Checks to see if the robot is currently facing West.
        {
            turnLeft(); // If the robot is facing West, turn it left to now face South.
        }
        else if (dir == Direction.NORTH) // Otherwise, checks to see if the robot is currently facing North.
        {
            turnBack(); // If the robot is facing North, turn it back to now face South.
        }
        else if (dir == Direction.EAST) // Otherwise, checks to see if the robot is currently facing East.
        {
            turnRight(); // If the robot is facing East, turn it right to now face South.
        }
    }

    /**
     * findWall will move the robot from its starting position until the robot is beside a wall (to the left) or there is a wall in front of the robot.
     *
     * @return Nothing.
     */
    private void findWall()
    {
        if (!isNextToLeftWall() && robot.frontIsClear()) // Checks to see the robot is not beside a wall (to the left)
        // and if there is no wall in front of the robot.
        {
            move(); // Move the robot one block forward each loop using the overrode method.
            findWall(); // Call this method again to have the robot move until it has found a wall, which is when the if statement becomes untrue (recursive call).
        }
    }

    /**
     * walkTheWall will will move the robot while there is a wall to the left of the robot, while there is no wall in front of the robot
     * and while the robot is not at the origin position.
     *
     * @return Nothing.
     */
    private void walkTheWall()
    {
        if (isNextToLeftWall() && robot.frontIsClear()) // Checks to see there is a wall to the left of the robot
        // and if there is no wall in front of the robot.
        {
            move(); // Moves the robot one block forward using the overrode move method.
            walkTheWall(); // Calls this method again to have the robot move along the wall until the if statement is not true (recursive call).
        }
        else // Otherwise (if the robot is at the corner of the wall)...
        {
            turnToNextWall(); // Use the helper method created to have it turn around the corner to the next wall.
        }
    }

    /**
     * turnToNextWall will test conditions and turn the robot to the next wall in the maze once it has reached a corner.
     */
    private void turnToNextWall()
    {
        if (!robot.frontIsClear() && isNextToLeftWall()) // If there is a wall in front, and to the left of the robot...
        {
            turnRight(); // Turn the robot to the right (has reached a left corner).
        }
        else // Otherwise (if there is no wall in front of the robot)...
        {
            turnLeft(); // Turn the robot to the left, because it is navigating around the polygon in such a way that the walls are to the LEFT of the robot.
        }

        if (!robot.frontIsClear()) // Checks if there is a wall in front of the robot.
        {
            // If there is, turn the robot to the right.
            turnRight();
        }

        move(); // Move the robot one block forward using the overrode move method.
    }


    /**
     * walkMaze will use the helper methods created to have the robot navigate its way
     * around the maze to the origin position, (0, 0).
     *
     * @return Nothing.
     */

    private void walkMaze()
    {
        if (!(robot.getAvenue() == xOrigin) || !(robot.getStreet() == yOrigin)) // Checks to see if the robot is at the origin position.
        {
            walkTheWall(); // Do the wall walking until we reach teh end of the wall
            walkMaze(); // Recursively call again until we reach origin
        }

        turnSouth(); // Finished -- Turn south to show finished
    }
}
