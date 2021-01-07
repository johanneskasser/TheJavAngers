package at.ac.fhcampuswien;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class SnakeBody extends Rectangle {

    public SnakeBody(int blocksize){
        super(blocksize, blocksize);
        Image headPIC = new Image(String.valueOf(new File("snakeBody.png")));
        ImagePattern imagePattern = new ImagePattern(headPIC);
        this.setFill(imagePattern);
    }
}
