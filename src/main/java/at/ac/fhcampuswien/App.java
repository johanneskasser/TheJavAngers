package at.ac.fhcampuswien;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import javafx.util.Duration;

import java.awt.*;

import static at.ac.fhcampuswien.App.Direction.*;


public class App extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    public static int scoreINT = 0;
    private final int BLOCKS_HORIZONTAL = 20;
    private final int BLOCKS_VERTICAL = 20;
    private final int BLOCKSIZE = 25;

    private Direction current_dir = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;

    private Timeline timeline = new Timeline();

    private ObservableList<Node> snake;



    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(BLOCKS_HORIZONTAL * BLOCKSIZE, BLOCKS_VERTICAL * BLOCKSIZE);
        //root.setStyle("-fx-background-color: #008800;");

        Text score = new Text("Score: " + scoreINT);
        score.setTextAlignment(TextAlignment.LEFT);
        score.setFont(Font.font(23));
        score.setFill(Color.BLACK);


        Group fullSnake = new Group();
        snake = fullSnake.getChildren();

        //Creating Food
        Rectangle food = new Rectangle(BLOCKSIZE, BLOCKSIZE);
        food.setFill(Color.RED);
        food.setTranslateX((int) (Math.random() * (BLOCKS_HORIZONTAL * BLOCKSIZE - BLOCKSIZE)) / BLOCKSIZE * BLOCKSIZE);
        food.setTranslateY((int) (Math.random() * (BLOCKS_VERTICAL * BLOCKSIZE - BLOCKSIZE)) / BLOCKSIZE * BLOCKSIZE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), event -> {
            if (!running)
                return;


            boolean hasEaten = snake.size() > 1;

            Node tail = hasEaten ? snake.remove(snake.size()-1) : snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch (current_dir) {
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCKSIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCKSIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCKSIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCKSIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if (hasEaten)
                snake.add(0,tail);

            for (Node rect : snake) {
                if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                        && tail.getTranslateY() == rect.getTranslateY()) {
                    restartGame();
                    break;
                }
            }

            if (tail.getTranslateX() < 0 || tail.getTranslateX() >= BLOCKS_HORIZONTAL * BLOCKSIZE ||
                    tail.getTranslateY() < 0 || tail.getTranslateY() >= BLOCKS_VERTICAL * BLOCKSIZE) {
                restartGame();
            }

            if(tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()) {
                food.setTranslateX((int) (Math.random() * (BLOCKS_HORIZONTAL * BLOCKSIZE - BLOCKSIZE)) / BLOCKSIZE * BLOCKSIZE);
                food.setTranslateY((int) (Math.random() * (BLOCKS_VERTICAL * BLOCKSIZE - BLOCKSIZE)) / BLOCKSIZE * BLOCKSIZE);

                Rectangle rect = new Rectangle(BLOCKSIZE, BLOCKSIZE);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);

                snake.add(rect);
                scoreINT += 100;
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(score, food, fullSnake);
        return root;

    }

    public void restartGame() {
        stopGame();
        startGame();
    }

    public void startGame() {
        current_dir = RIGHT;
        Rectangle head = new Rectangle(BLOCKSIZE,BLOCKSIZE);
        snake.add(head);
        timeline.play();
        running = true;
    }

    public void stopGame() {
        running = false;
        timeline.stop();
        snake.clear();
    }



    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent());


        scene.setOnKeyPressed(event -> {
            if (moved) {
                switch (event.getCode()) {
                    case W:
                        if (current_dir != DOWN)
                            current_dir = UP;
                        break;
                    case S:
                        if (current_dir != UP)
                            current_dir = DOWN;
                        break;
                    case A:
                        if (current_dir != RIGHT)
                            current_dir = LEFT;
                        break;
                    case D:
                        if (current_dir != LEFT)
                            current_dir = RIGHT;
                        break;
                }
            }

            moved = false;
        });

        primaryStage.setTitle("Snake - The JavAngers");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
    }
}
