package minesweeper;

import java.io.IOException;


import javax.swing.JFrame;

public class MineSweeper {

    
    public static void main(String[] argv) throws IOException {
       
        @SuppressWarnings("resource")
      

        Model model;
        
            int width = 9;
            int height = 9;
            int bombs = 10;
            model = new Model(width, height, bombs);
       
            model = new Model(width, height, bombs);
        
        //Creating the View
        View view = new View(model);

        JFrame frame = new JFrame("Minesweeper");

        frame.setContentPane(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

    }
}

