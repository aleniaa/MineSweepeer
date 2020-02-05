package minesweeper;

import java.util.Observable;

public class Field extends Observable {
    
    //No one should change this stuff from the Outside
    private int field_id;
    private int posx;
    private int posy;
    private Model model;
    private boolean is_revealed;        //If the field is Revealed
    private boolean is_flag;            //If the field is selected

 
    public Field(Model model, int x, int y, int field_id) {
        this.field_id = field_id;
        this.posx = x;
        this.posy = y;
        this.model = model;
        this.is_revealed = false;
        this.is_flag = false;

    }

    //Initialises the Field
     
    public void Init(Model model, int x, int y, int field_id) {
        this.field_id = field_id;
        this.posx = x;
        this.posy = y;
        this.model = model;
        this.is_revealed = false;
        this.is_flag = false;

    }

    /**
     * Reveal the field
     */
    public void reveal() {
        if (model.getState().equals("running")) {
            if (!this.model.getThread().isAlive()) {
                this.model.startThread();
            }

            if (this.field_id == 9) {
                this.model.setState("lost");

                this.is_revealed = true;
                this.setChanged();
                this.notifyObservers();
            } else if (this.is_revealed == false) {
                model.addToRevealed();
                this.is_revealed = true;
                if (this.field_id == 0) {
                    this.model.revealZeros(this);
                }
                this.setChanged();
                this.notifyObservers();

            }
        }

        if (!model.getState().equals("running")) {
            this.model.stopThread();
        }

    }

    /**
     * Changing the State
     */
    public void changeState() {
        if (!this.is_flag && !this.getRevealed() && model.getState().equals("running")) {

            if (!this.model.getThread().isAlive()) {
                this.model.startThread();
            }
            this.is_flag = true;
            this.model.subRemainingBombs();
            this.setChanged();
            this.notifyObservers();
        } else if (!this.getRevealed() && model.getState().equals("running")) {
            this.is_flag = false;
            this.model.addRemainingBombs();
            this.setChanged();
            this.notifyObservers();
        }

        if (!model.getState().equals("running")) {
            this.model.stopThread();
        }
    }

   
    public boolean isFlag() {
        return this.is_flag;
    }

    public int getField_id() {
        return field_id;
    }

    
    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    
    public boolean getRevealed() {
        return this.is_revealed;
    }

    public void add1() {
        this.field_id++;
    }

   
    public void setBomb() {
        this.field_id = 9;
    }

    
    public Model getModel() {
        return this.model;
    }

}
