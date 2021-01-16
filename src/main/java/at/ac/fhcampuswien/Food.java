package at.ac.fhcampuswien;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.File;

public class Food extends Rectangle{
    private final int blocksize;
    private final int BLOCKS_HORIZONTAL;
    private final int BLOCKS_VERTICAL;
    private String state = null;

    public Food(int blocksize, int BLOCKS_HORIZONTAL, int BLOCKS_VERTICAL){
        super(blocksize, blocksize);
        this.blocksize = blocksize;
        this.BLOCKS_HORIZONTAL = BLOCKS_HORIZONTAL;
        this.BLOCKS_VERTICAL = BLOCKS_VERTICAL;
    }

    private int generateNewRandomX(){
        return ((int) (Math.random() * (BLOCKS_HORIZONTAL * blocksize - blocksize)) / blocksize * blocksize);
    }

    private int generateNewRandomY(){
        return ((int) (Math.random() * (BLOCKS_VERTICAL * blocksize - blocksize)) / blocksize * blocksize);
    }

    public void reposition(){
        String picture = checkType();
        if (picture.equals("Images/food.png")){
            this.setState("Good");
        } else {
            this.setState("Bad");
        }
        Image look = new Image(String.valueOf(new File(picture)));
        ImagePattern imagePattern = new ImagePattern(look);
        this.setFill(imagePattern);
        this.setTranslateX(generateNewRandomX());
        this.setTranslateY(generateNewRandomY());
    }

    private String checkType(){
        int random = (int)(Math.random() * (1 + 1) + 0);
        return switch (random) {
            case 0 -> "Images/food.png";
            case 1 -> "Images/BADfood.png";
            default -> throw new IllegalStateException("Unexpected value: " + random);
        };
    }

    public Rectangle getFood() {
        return this;
    }

    private void setState(String state){
        this.state = state;
    }

    public void changeState(String state){
        this.setState(state);
        Image image = new Image(String.valueOf(new File("Images/food.png")));
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }

    public String getState(){
        return this.state;
    }

    public int getPosX(){
        return (int)getFood().getTranslateX();
    }

    public int getPosY(){
        return (int)getFood().getTranslateY();
    }
}
