package at.ac.fhcampuswien;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    private static final int BLOCKS_HORIZONTAL = 30;
    private static final int BLOCKS_VERTICAL = 30;
    private static final int BLOCKSIZE = 30;

    private Direction current_dir = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;
    private final Timeline timeline = new Timeline();

    private ObservableList<Node> snake;
    private final Player currPlayer = new Player();

    public static int getBLOCKS_HORIZONTAL() {
        return BLOCKS_HORIZONTAL;
    }

    public static int getBLOCKS_VERTICAL() {
        return BLOCKS_VERTICAL;
    }


    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(BLOCKS_HORIZONTAL * BLOCKSIZE, BLOCKS_VERTICAL * BLOCKSIZE);
        root.setStyle("-fx-background-image: url('Images/Playground2.png');" +
                        "-fx-background-position: center center;" +
                        "-fx-background-repeat: stretch;");

        Text score = new Text();
        Text gameInformation = new Text();

        Button changeDifficultyButton = new Button("Change Difficulty");
        changeDifficultyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pauseGame();
                currPlayer.updatePlayer();
                changeDifficultyOfGame(currPlayer.getDiffD());
            }
        });

        //Create Snake Group, stores all Nodes of snake
        Group fullSnake = new Group();
        //Store all objects of fullSnake in ObservableList<Node>
        snake = fullSnake.getChildren();

        //Creating Food
        Food food = new Food(BLOCKSIZE);
        food.reposition();

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.15), event -> {
            String[] information = HighScore.get();
            String playerWithSetHighScore = information[0];
            int currentHighScore = Integer.parseInt(information[1]);

            score.setText("Score: " + currScore.getScoreINT() +
                    "\nHighscore: " + currentHighScore +
                    "\nHighscore set by: " + playerWithSetHighScore +
                    "\nPlayer: " + currPlayer.getName() +
                    "\nDifficulty: " + currPlayer.getDifficultySting());
            score.setX(30.0);
            score.setY(40.0);
            score.setFont(Font.font("Verdana", 20));
            score.setFill(Color.RED);
            gameInformation.setText("Press ESC to pause the Game \n" +
                    "Press ENTER to resume the Game");
            gameInformation.setX((BLOCKS_HORIZONTAL * BLOCKSIZE)-300);
            gameInformation.setY((BLOCKS_VERTICAL * BLOCKSIZE)-65);
            gameInformation.setFont(Font.font("Verdana",15));
            gameInformation.setFill(Color.RED);

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
                    currScore.resetScore();
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

                SnakeBody snakeBody = new SnakeBody(BLOCKSIZE, "Body");
                snakeBody.setTranslateX(tailX);
                snakeBody.setTranslateY(tailY);

                snake.add(snakeBody);
                currScore.raiseScore();
                HighScore.checkScore(currScore.getScoreINT(), currPlayer.getName());
            }

        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(score, gameInformation, food, fullSnake, changeDifficultyButton);
        return root;
    }

    public void restartGame(){
        stopGame();
        startGame();
    }

    public void startGame(){
        current_dir = RIGHT;
        SnakeBody bodyElementHead = new SnakeBody(BLOCKSIZE, "Head");
        snake.add(bodyElementHead);
        SnakeBody bodyElementTail = new SnakeBody(BLOCKSIZE, "Tail");
        snake.add(bodyElementTail);
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
        timeline.pause();
    }

    public void pauseGamewithESC() {
        running = false;
        timeline.pause();
        new DialogWindow().showPauseScreen();
        resumeGame();
    }

    public void resumeGame() {
        running = true;
        timeline.play();
    }

    public void initGame(){
        currPlayer.checkPlayer();
        pauseGame();
        timeline.setRate(currPlayer.getDiffD());
        startGame();
    }

    public void changeDifficultyOfGame(double difficulty){
        timeline.setRate(difficulty);
        resumeGame();
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
                        pauseGamewithESC();
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
        initGame();
    }
}
