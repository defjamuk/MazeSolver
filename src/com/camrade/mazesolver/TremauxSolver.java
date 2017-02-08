package com.camrade.mazesolver;

import becker.robots.Direction;

import java.util.*;

/**
 * Created by camrade on 05/02/17.
 * Uses the maze solving algorithm by Charles Pierre Trémaux
 */
public class TremauxSolver extends BaseSolver implements ISolver {

    // Local Coodinate class
    private class Coord{
        int x;
        int y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private enum RobotDirection {
        FORWARD, LEFT, RIGHT, BACKWARD
    }

    private List<Coord> visited = new ArrayList<>();
    private List<Coord> blocked = new ArrayList<>();

    public TremauxSolver(SolverConfig config) {
        super(config);
    }

    @Override
    public void RunSolver() {
        RunTremauxMethod();
    }

    private void RunTremauxMethod() {
        while (!isAtGoal()) {

            addPositionToVisited();
            if (validJunction()) { //Visited cells are treated as walls. Junctions return true if there is no wall to the left or right
                RobotDirection direction = getRandomAvailableDirection();
                assert direction != null : "valid junction but random direction was null";
                turnRobot(direction);
                robot.move();
            } else if (canMoveForward()) { //Visited cells are treated as walls
                robot.move();
            } else {
                while (!validJunction()) {
                    retraceStep();
                }
            }
        }
    }

    /**
     * At a valid junction if there is more than one exit
     * */
    private boolean validJunction() {

        int clearCnt = 0;

        clearCnt += canMoveForward() ? 1 : 0;

        robot.turnLeft();
        clearCnt += canMoveForward() ? 1 : 0;

        robot.turnRight(2);
        clearCnt += canMoveForward() ? 1 : 0;

        robot.turnLeft();
        return (clearCnt > 1);
    }

    /**
     * At a valid junction if left or right is not a wall
     * */
    private boolean atJunction() {

        int clearCnt = 0;

        clearCnt += robot.frontIsClear() ? 1 : 0;

        robot.turnLeft();
        clearCnt += robot.frontIsClear() ? 1 : 0;

        robot.turnRight(2);
        clearCnt += robot.frontIsClear() ? 1 : 0;

        robot.turnLeft();
        return (clearCnt > 1);
    }

    /**
     * Check if we have been here or if there is a wall
     * */
    private boolean canMoveForward() {

        if (!robot.frontIsClear())
            return false;

        int x = robot.getAvenue();
        int y = robot.getStreet();

        Direction dir = robot.getDirection();

        switch(dir){
            case EAST:
                return !checkVisited(new Coord(x+1, y));
            case WEST:
                return !checkVisited(new Coord(x-1, y));
            case NORTH:
                return !checkVisited(new Coord(x, y-1));
            case SOUTH:
                return !checkVisited(new Coord(x, y+1));
        }

        return true;
    }

    /**
     * Checks to see we can turn left
     * */
    private boolean canTurnLeft(){
        boolean chkTurn = false;

        robot.turnLeft();
        chkTurn = robot.frontIsClear();
        robot.turnRight();

        return chkTurn;
    }

    /**
     * Checks to see we can turn right
     * */
    private boolean canTurnRight(){
        boolean chkTurn = false;

        robot.turnRight();
        chkTurn = robot.frontIsClear();
        robot.turnLeft();

        return chkTurn;
    }

    /**
     * Checks we have been here before
     * */
    private boolean checkVisited(Coord coord){

        for(Coord c : visited){
            if (c.x == coord.x && c.y == coord.y)
                return true;
        }

        return false;
    }

    /**
     * Point the robot in the correct direction
     * */
    private void turnRobot(RobotDirection direction) {

        switch(direction){
            case BACKWARD:
                robot.turnAround();
                break;
            case FORWARD:
                break;
            case LEFT:
                robot.turnLeft();
                break;
            case RIGHT:
                robot.turnRight();
        }
    }

    /**
     * Keeps a record of visited points
     * */
    private void addPositionToVisited() {

        Coord c = new Coord(robot.getAvenue(), robot.getStreet());

        if (!checkVisited(c))
            this.visited.add(c);
    }

    /**
     * Has a stab at a direction
     * */
    private RobotDirection getRandomAvailableDirection() {
        final List<RobotDirection> availableDirections = getAvailableDirections();
        if ( availableDirections.isEmpty() ) {
            return null; // or throw an exception
        }

        Random rn = new Random();
        final int idx = rn.nextInt(availableDirections.size());
        return availableDirections.get(idx);
    }

    private List<RobotDirection> getAvailableDirections() {
        ArrayList<RobotDirection> dirs = new ArrayList<>();

        robot.turnLeft();
        if (canMoveForward())
            dirs.add(RobotDirection.LEFT);

        robot.turnLeft(2);
        if (canMoveForward())
            dirs.add(RobotDirection.RIGHT);

        robot.turnLeft();
        if (canMoveForward())
            dirs.add(RobotDirection.FORWARD);

        return dirs;
    }

    /**
     * Retraces steps using wall walking until we
     * get to valid junction or arrive at goal
     * */
    private void retraceStep() {
        if(isAtGoal())
            return;

        if (isNextToLeftWall() && robot.frontIsClear()){
            robot.move(); // Moves the robot one block forward using the overrode move method.
        }
        else
            turnToNextWall(); // Use the helper method created to have it turn around the corner to the next wall.
    }

    /**
     * Next wall
     * */
    private void turnToNextWall()
    {
        if (!robot.frontIsClear() && isNextToLeftWall())
            robot.turnRight();
        else
            robot.turnLeft();

        if (!robot.frontIsClear())
            robot.turnRight();

        robot.move();
    }

    private boolean isNextToLeftWall(){
        boolean nextToWall = true;

        robot.turnLeft();

        if (robot.frontIsClear())
            nextToWall = false;

        robot.turnRight();

        return nextToWall;
    }

    /**
     * Have we arrived?
     * */
    private boolean isAtGoal(){
        return ((robot.getAvenue() == xOrigin) && (robot.getStreet() == yOrigin));
    }
}
