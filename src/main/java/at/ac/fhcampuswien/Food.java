package at.ac.fhcampuswien;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle{
    private int blocksize;

    public Food(int blocksize) {
        super(blocksize, blocksize);
        this.blocksize = blocksize;
        this.setFill(Color.RED);
    }

    private int generateNewRandomX(){
        return ((int) (Math.random() * (App.getBLOCKS_HORIZONTAL() * blocksize - blocksize)) / blocksize * blocksize);
    }

    private int generateNewRandomY(){
        return ((int) (Math.random() * (App.getBLOCKS_VERTICAL() * blocksize - blocksize)) / blocksize * blocksize);
    }

    public void reposition(){
        this.setTranslateX(generateNewRandomX());
        this.setTranslateY(generateNewRandomY());
    }

    public Rectangle getFood() {
        return this;
    }

    public int getPosX(){
        return (int)getFood().getTranslateX();
    }

    public int getPosY(){
        return (int)getFood().getTranslateY();
    }
}
