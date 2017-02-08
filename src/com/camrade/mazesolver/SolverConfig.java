package com.camrade.mazesolver;

import becker.robots.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by camrade on 05/02/17.
 * Utility class to load and optionally save config settings
 */
public class SolverConfig {

    private R2D2 robot;
    private Alderaan city;
    private int xOrigin = 0;
    private int yOrigin = 0;
    private String settingsFile = null;

    public int getxOrigin() {
        return xOrigin;
    }

    public int getyOrigin() {
        return yOrigin;
    }

    /*
    * Property getter to get Robot. Lazy loads settings if not already called
    * */
    public R2D2 getRobot() {
        if (robot == null)
            LoadSettings();

        return robot;
    }

    /*
    * Property getter to get City. Lazy loads settings if not already called
    * */
    public Alderaan getCity() {
        if (city == null)
            LoadSettings();

        return city;
    }

    /**
     * File path of the settings file to load
     * */
    public SolverConfig(String settingsFile) {
        this.settingsFile = settingsFile;
    }

    public SolverConfig() {
        this.settingsFile = null;
    }

    public void LoadSettings(){

        if (this.settingsFile != null)
            loadFile();
        else{
            city = new Alderaan(8,8,1,1);
            robot = new R2D2 (this.city, 3, 4, Direction.SOUTH,1000); // Creates a robot to complete the robot task in the maze starting at street 3, avenue 4 facing South with no things.
        }
    }

    private void loadFile(){

        this.city = new Alderaan(8,8,1,1);

        String line = null;
        try
        {
            FileReader MazeText = new FileReader(settingsFile);

            BufferedReader bufferedReader =
                    new BufferedReader(MazeText);
            int row = 0;

            int lineCount = 0;
            int col = 0;
            while((line = bufferedReader.readLine()) != null) {
                lineCount++;
                col = line.length();
            }
            bufferedReader.close();
            MazeText.close();
            char[][] MazeArray = new char[col][lineCount];
            MazeText = new FileReader(settingsFile);
            bufferedReader = new BufferedReader(MazeText);

            while((line = bufferedReader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    switch(line.charAt(i))
                    {

                        case '1':
                            Wall MazeWall1 = new Wall(city, row,i, Direction.SOUTH);
                            Wall MazeWall2 = new Wall(city, row,i, Direction.NORTH);
                            Wall MazeWall3 = new Wall(city, row,i, Direction.EAST);
                            Wall MazeWall4 = new Wall(city, row,i, Direction.WEST);
                            break;
                        //draws the original maze^ and stores in 2D char array


                    }
                    MazeArray[i][row] = line.charAt(i);
                }

                row++;
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex)
        {
            //File not found error
            System.out.println(
                    "Unable to open file '" +
                            settingsFile + "'");
        }
        catch(IOException ex)
        {
            System.out.println(
                    "Error reading file '"
                            + settingsFile + "'");
            ex.printStackTrace();
        }

        robot = new R2D2 (this.city, 3, 4, Direction.SOUTH,0); // Creates a robot to complete the robot task in the maze starting at street 3, avenue 4 facing South with no things.

    }

    public void SaveSettings(){

    }
}
