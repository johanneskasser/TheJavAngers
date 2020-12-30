package at.ac.fhcampuswien;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Duration;

import static at.ac.fhcampuswien.App.Direction.*;


public class App extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    Score currScore = new Score();
    private static final int BLOCKS_HORIZONTAL = 40;
    private static final int BLOCKS_VERTICAL = 40;
    private static final int BLOCKSIZE = 20;

    private Direction current_dir = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;
    private Timeline timeline = new Timeline();

    private ObservableList<Node> snake;

    public static int getBLOCKS_HORIZONTAL() {
        return BLOCKS_HORIZONTAL;
    }

    public static int getBLOCKS_VERTICAL() {
        return BLOCKS_VERTICAL;
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(BLOCKS_HORIZONTAL * BLOCKSIZE, BLOCKS_VERTICAL * BLOCKSIZE);

        Text score = new Text();
        Text gameInformation = new Text();

        //Create Snake Group, stores all Nodes of snake
        Group fullSnake = new Group();
        //Store all objects of fullSnake in ObservableList<Node>
        snake = fullSnake.getChildren();

        //Creating Food
        Food food = new Food(BLOCKSIZE);
        food.setFill(Color.RED);


        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.15), event -> {
            score.setText("Score: " + currScore.getScoreINT() + "\nHighscore: " + HighScore.get());
            score.setX(10.0);
            score.setY(30.0);
            score.setFont(Font.font("Arial", 15));
            score.setFill(Color.BLACK);
            gameInformation.setText("Press ESC to pause the Game \n" +
                    "Press ENTER to resume the Game");
            gameInformation.setX((BLOCKS_HORIZONTAL * BLOCKSIZE)-170);
            gameInformation.setY((BLOCKS_VERTICAL * BLOCKSIZE)-30);
            gameInformation.setFont(Font.font("Arial",10));
            gameInformation.setFill(Color.BLACK);

            if (!running)
                return;


            boolean hasEaten = snake.size() > 1;

            Node tail = hasEaten ? snake.remove(snake.size()-1) : snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch (current_dir) {
                case UP -> {
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCKSIZE);
                }
                case DOWN -> {
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCKSIZE);
                }
                case LEFT -> {
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCKSIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                }
                case RIGHT -> {
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCKSIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                }
            }

            moved = true;

            if (hasEaten)
                snake.add(0,tail);

            for (Node rect : snake) {
                //Collision detection!
                if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                        && tail.getTranslateY() == rect.getTranslateY()) {
                    //Snake went OOB, reset game.
                    //HighScore.checkScore(scoreINT);
                    restartGame();
                    currScore.raiseScore();
                    break;
                }
            }

            if (tail.getTranslateX() < 0 || tail.getTranslateX() >= BLOCKS_HORIZONTAL * BLOCKSIZE ||
                    tail.getTranslateY() < 0 || tail.getTranslateY() >= BLOCKS_VERTICAL * BLOCKSIZE) {
                //Snake went OOB, reset game.
                //HighScore.checkScore(scoreINT);
                restartGame();
                currScore.resetScore();
            }

            if(tail.getTranslateX() == food.getPosX() && tail.getTranslateY() == food.getPosY()) {
                //Snake got something to eat.
                food.reposition();

                Rectangle rect = new Rectangle(BLOCKSIZE, BLOCKSIZE);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);

                snake.add(rect);
                currScore.raiseScore();
                HighScore.checkScore(currScore.getScoreINT());
            }

        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(score, gameInformation, food, fullSnake);
        return root;
    }

    public void restartGame() {
        stopGame();
        startGame();
    }

    public void startGame() {
        current_dir = RIGHT;
        Rectangle head = new Rectangle(BLOCKSIZE, BLOCKSIZE);
        snake.add(head);
        timeline.play();
        running = true;
    }

    public void stopGame() {
        running = false;
        timeline.stop();
        snake.clear();
    }

    public void pauseGame() {
        running = false;
        timeline.stop();
    }

    public void resumeGame() {
        running = true;
        timeline.play();
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
                    case ESCAPE:
                        pauseGame();
                        break;
                }
            }
            moved = false;
            if(!running){
                if(event.getCode() == KeyCode.ENTER){
                    resumeGame();
                }
            }
        });

        primaryStage.setTitle("Snake - The JavAngers");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
    }
}
