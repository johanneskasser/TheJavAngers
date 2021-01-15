package at.ac.fhcampuswien;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.File;

public class Food extends Rectangle{
    private final int blocksize;
    private final int BLOCKS_HORIZONTAL;
    private final int BLOCKS_VERTICAL;

    public Food(int blocksize, int BLOCKS_HORIZONTAL, int BLOCKS_VERTICAL){
        super(blocksize, blocksize);
        Image pizza = new Image(String.valueOf(new File("Images/food.png")));
        ImagePattern imagePattern = new ImagePattern(pizza);
        this.blocksize = blocksize;
        this.BLOCKS_HORIZONTAL = BLOCKS_HORIZONTAL;
        this.BLOCKS_VERTICAL = BLOCKS_VERTICAL;
        this.setFill(imagePattern);
    }

    private int generateNewRandomX(){
        return ((int) (Math.random() * (BLOCKS_HORIZONTAL * blocksize - blocksize)) / blocksize * blocksize);
    }

    private int generateNewRandomY(){
        return ((int) (Math.random() * (BLOCKS_VERTICAL * blocksize - blocksize)) / blocksize * blocksize);
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
