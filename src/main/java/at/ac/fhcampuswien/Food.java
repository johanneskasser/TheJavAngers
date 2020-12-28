package at.ac.fhcampuswien;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle{
    private int blocksize;
    private final Rectangle food = new Rectangle(blocksize, blocksize);

    public Food(int blocksize) {
        this.blocksize = blocksize;
        food.setHeight(blocksize);
        food.setWidth(blocksize);
        food.setFill(Color.RED);
    }

    private int generateNewRandomX(){
        return (int)(Math.random() * (App.getBLOCKS_HORIZONTAL() * blocksize - blocksize)) / (blocksize * blocksize);
    }

    private int generateNewRandomY(){
        return (int)(Math.random() * (App.getBLOCKS_VERTICAL() * blocksize - blocksize)) / (blocksize * blocksize);
    }

    public void reposition(){
        food.setTranslateX(generateNewRandomX());
        food.setTranslateY(generateNewRandomY());
    }

    public Rectangle getFood() {
        return food;
    }

    public int getPosX(){
        return (int)this.getFood().getTranslateX();
    }

    public int getPosY(){
        return (int)this.getFood().getTranslateY();
    }
}
