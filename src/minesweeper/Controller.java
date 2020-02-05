
package minesweeper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Controller extends MouseAdapter {

    private Field field;
    private Model model;

    
    public Controller(Field field) {
        this.field = field;
    }

    
    public void updateField(Field field) {
        this.field = field;
    }

    
    public Controller(Model model) {
        this.model = model;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                if (this.model == null) {
                    if (!field.isFlag()) {
                        field.reveal();

                    }
                } else {
                    model.Init();
                }
                break;
            case MouseEvent.BUTTON3:
                if (this.model == null) {
                    field.changeState();
                }
                break;
            default:
                break;
        }
    }
}