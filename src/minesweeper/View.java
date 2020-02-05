
package minesweeper;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class View extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;
    private final JPanel view;
    private JLabel bombs, gameState;
    private final ButtonView[][] fields;
    private final Model model;

    
    public View(Model model) {
        this.model = model;
        this.setLayout(new BorderLayout());
        this.view = new JPanel();
        this.bombs = setLabel(this.bombs, "Bombs:  " + Integer.toString(model.remainingBombs()));
        this.gameState = setLabel(this.gameState, "Status:  " + model.getState());
       

        this.add(this.bombs, BorderLayout.WEST);
        this.add(this.gameState, BorderLayout.EAST);
        
        this.add(restartButton(), BorderLayout.NORTH);
        this.fields = new ButtonView[model.getHeight()][model.getWidth()];
        this.model.addObserver(this);

        this.view.setLayout(new GridLayout(model.getHeight(), model.getWidth()));
        buildbuttons();
        this.add(view, BorderLayout.SOUTH);

    }

    // updates the view
     
    @Override
    public void update(Observable obs, Object o) {
		

        // If object is empty, then update all
        if (o != null) {
            updateButtons();
        }
        this.bombs = setLabel(this.bombs, "Bombs:  " + Integer.toString(model.remainingBombs()));
        this.gameState = setLabel(this.gameState, "Status:  " + model.getState());
        

    }

    
    private JLabel setLabel(JLabel label, String string) {
        if (!(label instanceof JLabel)) {
            label = new JLabel("");
        }
        label.setText(string);
        label.setPreferredSize(new Dimension(100, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;

    }

    
    public JPanel getview() {
        return this.view;
    }

    
    public JButton restartButton() {
        JButton button = new JButton("Restart");
        button.setPreferredSize(new Dimension(20, 40));
        Controller controller = new Controller(model);
        button.addMouseListener(controller);
        return button;

    }

    //update button 
    
    public void updateButtons() {

        removeButtons();

        buildbuttons();

    }

    //delete button
    private void removeButtons() {
        for (int i = 0; i < this.model.getHeight(); i++) {
            for (int j = 0; j < this.model.getWidth(); j++) {

                this.view.remove(fields[i][j].getButton());

            }

        }

    }

    // create buttons
    private void buildbuttons() {

        for (int i = 0; i < this.model.getHeight(); i++) {
            for (int j = 0; j < this.model.getWidth(); j++) {
                ButtonView button = new ButtonView(this.model.getField(i, j));
                fields[i][j] = button;
                this.view.add(button.getButton());

            }

        }
    }
}
