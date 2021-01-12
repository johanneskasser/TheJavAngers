package at.ac.fhcampuswien;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.File;

public class Food extends Rectangle{
    private final int blocksize;

    public Food(int blocksize){
        super(blocksize, blocksize);
        Image pizza = new Image(String.valueOf(new File("Images/food.png")));
        ImagePattern imagePattern = new ImagePattern(pizza);
        this.blocksize = blocksize;
        this.setFill(imagePattern);
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
