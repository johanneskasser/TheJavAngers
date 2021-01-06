package at.ac.fhcampuswien;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.MalformedURLException;

public class Food extends Rectangle{
    private int blocksize;

    public Food(int blocksize) throws MalformedURLException {
        super(blocksize, blocksize);
        Image pizza = new Image(new File("build/tmp/pizza.jpg").toURI().toURL().toString());
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
