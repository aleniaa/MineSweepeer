
package minesweeper;


import java.util.Observable;

public class Model extends Observable {

    
    private Field[][] field;
    private int height;
    private int width;
    private int bombs;
    private int bombs_left;
    private String state;
    private int revealed;
    private Thread thread;	
    private boolean running;	//boolean running for terminating the thread

   
    public Model(int width, int height, int bombs) {

        //Creates the Game field as an Array
        this.field = new Field[height][width];
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        this.bombs_left = bombs;
        this.revealed = 0;
        this.state = "running";
        this.running = false;
        this.thread = new Thread();

        buildGameBoard();

    }

    //Initialize everything
     
    public void Init() {
        //initialization as "lost"
        this.state = "lost";
        this.setChanged();
        this.notifyObservers();
        //reset all the fields
        resetAllFields();
        this.state = "running";
        this.bombs_left = this.bombs;
        this.revealed = 0;

        resetThread();
        buildGameBoard();
        this.setChanged();
        this.notifyObservers(true);

    }

    /**
     * Creates the Thread for the Timer
     */
    private void setThread() {

        this.thread = new Thread() {
            @SuppressWarnings("static-access")
            
            @Override
            public void run() {
                while (running) {

                    try {

                        
                        setChanged();
                        notifyObservers();
                        this.sleep(1000);

                    } catch (InterruptedException e) {
                        
                        e.printStackTrace();
                    }

                }

            }
        };
        this.thread.start();

    }

   

    public void buildGameBoard() {
        //set all the fields
        setAllFields();

        //"random" selection of Bombs
        for (int i = 0; i < bombs; i++) {
            int width, height;

            do {

                width = (int) (Math.random() * (this.width));
                height = (int) (Math.random() * (this.height));

            } while (this.field[height][width].getField_id() == 9);

            this.field[height][width].setBomb();
            bombcounter(getField(height, width));
        }
    }

    /**
     *
     * @param y height
     * @param x width
     * @return count of bombs around
     */
    public int nearbombs(int y, int x) {

        int result = 0;
        int ax;
        int ay;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                ay = y + i;
                ax = x + j;
                if (ay >= 0 && ay < this.height && ax >= 0 && ax < this.width) {
                    if (this.field[ay][ax].getField_id() == 9) {
                        result++;
                    }
                }

            }
        }
        return result;

    }

    
    public void bombcounter(Field field) {
        int ax;
        int ay;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                ay = field.getPosy() + i;
                ax = field.getPosx() + j;

                if (ay >= 0 && ay < this.height && ax >= 0 && ax < this.width) {
                    if (this.field[ay][ax].getField_id() != 9) {
                        this.field[ay][ax].add1();
                    }
                }

            }
        }
    }

    /**
     * Opens all zeros and reveals them
     *
     * @param field
     */
    public void revealZeros(Field field) {
        int x = field.getPosx();
        int y = field.getPosy();

        int ax;
        int ay;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                ay = y + i;
                ax = x + j;

                if (ay >= 0 && ay < this.height && ax >= 0 && ax < this.width) {
                    if (!this.field[ay][ax].isFlag()) {
                        this.field[ay][ax].reveal();
                    }
                }

            }
        }

    }


    public void setState(String state) {
        this.state = state;
        this.setChanged();
        this.notifyObservers();
    }

   
    private void setAllFields() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.field[i][j] = new Field(this, j, i, 0);
            }
        }
    }

   
    private void resetAllFields() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.field[i][j].Init(this, j, i, 0);
            }
        }
    }

   
    public void addToRevealed() {
        this.revealed++;
        if (this.revealed >= ((this.width * this.height) - bombs)) {
            setState("won");

        }
        this.setChanged();
        this.notifyObservers();
    }

    
    public void addRemainingBombs() {
        this.bombs_left++;
        this.setChanged();
        this.notifyObservers();
    }

     
    public void subRemainingBombs() {
        this.bombs_left--;
        this.setChanged();
        this.notifyObservers();
    }

    
    public void startThread() {
        this.running = true;
        this.setThread();
    }

    
    public void resetThread() {
        this.running = false;
        this.setChanged();
        this.notifyObservers();
    }

    public void stopThread() {
        this.running = false;

    }

   
    public Thread getThread() {
        return this.thread;
    }

   
    public int getHeight() {
        return this.height;
    }

    
    public int getWidth() {
        return this.width;
    }

    // return game state
    
    public String getState() {
        return this.state;
    }

   
    public Field getField(int height, int width) {
        return this.field[height][width];
    }

    
    public Field[][] getFields() {
        return this.field;
    }

    // return number of bombs
    
    public int getBombs() {
        return this.bombs;
    }

    //return remaining bombs
    public int remainingBombs() {
        return this.bombs_left;
    }

 

}