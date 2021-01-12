package at.ac.fhcampuswien;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class SnakeBody extends Rectangle {

    public SnakeBody(int blocksize, String type){
        super(blocksize, blocksize);
        Image headPIC = new Image(String.valueOf(new File("Images/snakeBody2.png")));
        Image bodyPIC = new Image(String.valueOf(new File("Images/snakeHead2.png")));
        Image tailPIC = new Image(String.valueOf(new File("Images/snakeTail.png")));
        ImagePattern imagePatternHead = new ImagePattern(headPIC);
        ImagePattern imagePatternBody = new ImagePattern(bodyPIC);
        ImagePattern imagePatternTail = new ImagePattern(tailPIC);
        switch(type){
            case "Head" -> this.setFill(imagePatternHead);
            case "Body" -> this.setFill(imagePatternBody);
            case "Tail" -> this.setFill(imagePatternTail);
        }
    }
}
