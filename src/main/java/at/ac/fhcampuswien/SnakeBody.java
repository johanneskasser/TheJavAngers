package at.ac.fhcampuswien;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class SnakeBody extends Rectangle {
    Image headUP = new Image(String.valueOf(new File("Images/snakeHead2UP.png")));
    Image headRIGHT = new Image(String.valueOf(new File("Images/snakeHead2RIGHT.png")));
    Image headDOWN = new Image(String.valueOf(new File("Images/snakeHead2DOWN.png")));
    Image headLEFT = new Image(String.valueOf(new File("Images/snakeHead2LEFT.png")));
    ImagePattern imagePatternUP = new ImagePattern(headUP);
    ImagePattern imagePatternRIGHT = new ImagePattern(headRIGHT);
    ImagePattern imagePatternDOWN = new ImagePattern(headDOWN);
    ImagePattern imagePatternLEFT = new ImagePattern(headLEFT);

    Image bodyHORZ = new Image(String.valueOf(new File("Images/snakeBody2HORZ.png")));
    Image bodyVERT = new Image(String.valueOf(new File("Images/snakeBody2VERT.png")));
    ImagePattern imagePatternHORZ = new ImagePattern(bodyHORZ);
    ImagePattern imagePatternVERT = new ImagePattern(bodyVERT);

    public SnakeBody(int blocksize){
        super(blocksize, blocksize);
        this.setFill(imagePatternHORZ);
    }

    public void switchType(String type, App.Direction curr_dir){
        if(type.equals("Head")) {
            switch (curr_dir) {
                case UP -> this.setFill(imagePatternUP);
                case DOWN -> this.setFill(imagePatternDOWN);
                case LEFT -> this.setFill(imagePatternLEFT);
                case RIGHT -> this.setFill(imagePatternRIGHT);
            }
        } else {
            switch(curr_dir){
                case UP, DOWN -> this.setFill(imagePatternVERT);
                case LEFT, RIGHT -> this.setFill(imagePatternHORZ);
            }
        }
    }
}
