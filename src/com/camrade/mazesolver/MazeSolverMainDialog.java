package com.camrade.mazesolver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Camrade on 30/01/17.
 * Main dialog for launching the app
 */
public class MazeSolverMainDialog {
    private JButton btnStartApp;
    private JPanel panelMain;
    private JTextPane robotMazeSolvingApplicationTextPane;
    private JButton btnChooseSettings;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;

    private ISolver solver;

    private enum SolverType{
        Trumeaux, WallFollower
    }

    private SolverType solvEnum = SolverType.WallFollower;

    public MazeSolverMainDialog() {
        btnStartApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Worker to start Becker GUI
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws InterruptedException {
                        SolverConfig config = new SolverConfig();

                        ISolver mySolv;

//                        if (solvEnum == SolverType.Trumeaux)
//                            mySolv = new TremauxSolver(config);
//                        else
//                            mySolv = new WallSolver(config);



                        mySolv = new DeadEndFillSolver(config);

                        RobotSolvingTask task = new RobotSolvingTask(mySolv);
                        task.PerformSolve();
                        return null;
                    }
                };

                worker.execute();
            }
        });

        btnChooseSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Trunaux Solver Radio
        radioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solvEnum = SolverType.Trumeaux;
            }
        });
    }

    /*
    * App start point
    * Create a dialog and wait
    * */
    public static void main(String[] args) {
        JFrame appFrame = new JFrame("R2D2 Escapes Alderaan");
        appFrame.setContentPane(new MazeSolverMainDialog().panelMain);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setLocationRelativeTo(null);
        appFrame.pack();
        appFrame.setVisible(true);
    }

}
