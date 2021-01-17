package at.ac.fhcampuswien;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.File;

import static at.ac.fhcampuswien.App.Direction.*;


public class App extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private final double screenBoundHeight = Screen.getPrimary().getBounds().getHeight();
    private final int BLOCKSIZE = (int)(screenBoundHeight / 30);

    private Direction current_dir = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;
    private final Timeline timeline = new Timeline();

    private ObservableList<Node> snake;
    private final Player currPlayer = new Player();
    Banner statementsBanner = new Banner();


    private Parent createContent(int BLOCKS_HORIZONTAL, int BLOCKS_VERTICAL) {
        Pane root = new Pane();
        root.setPrefSize(BLOCKS_HORIZONTAL * BLOCKSIZE, BLOCKS_VERTICAL * BLOCKSIZE);
        Image background = new Image(String.valueOf(new File("Images/Playground2.png")));
        BackgroundImage backgroundImage = new BackgroundImage(
                background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0,1.0,true, true, false, false)
        );
        root.setBackground(new Background(backgroundImage));


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

        //Creating Food, good and bad
        Food food = new Food(BLOCKSIZE, BLOCKS_HORIZONTAL, BLOCKS_VERTICAL);
        food.reposition();
        Food foodToo = new Food(BLOCKSIZE, BLOCKS_HORIZONTAL, BLOCKS_VERTICAL);
        foodToo.reposition();

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.15), event -> {
            String[] information = HighScore.get();
            String playerWithSetHighScore = information[0];
            int currentHighScore = Integer.parseInt(information[1]);

            score.setText("Score: " + currPlayer.getScore() +
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

            //Centering Statement Banner in the middle of the pane.
            statementsBanner.layoutXProperty().bind(root.widthProperty().subtract(statementsBanner.prefWidth(-1)).divide(2));
            statementsBanner.layoutYProperty().bind(root.heightProperty().subtract(statementsBanner.prefHeight(-1)).divide(2));

            switch (current_dir) {
                //define what happens when enum Direction is called. == Movement of the Snake in the following Directions
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
                    updateBanner(1);
                    break;
                }
            }

            if (tail.getTranslateX() < 0 || tail.getTranslateX() >= BLOCKS_HORIZONTAL * BLOCKSIZE ||
                    tail.getTranslateY() < 0 || tail.getTranslateY() >= BLOCKS_VERTICAL * BLOCKSIZE) {
                //Snake went OOB, reset game.
                //HighScore.checkScore(scoreINT);
                updateBanner(2);
            }

            if(food.getState().equals("Bad") && foodToo.getState().equals("Bad")){
                int random = (int)(Math.random() * (1 + 1) + 0);
                switch (random){
                    case 0:
                        food.changeState("Good");
                    case 1:
                        foodToo.changeState("Good");
                }
            }

            if(tail.getTranslateX() == food.getPosX() && tail.getTranslateY() == food.getPosY()) {
                //Snake got something to eat.
                if(food.getState().equals("Good")){
                    food.reposition();

                    SnakeBody snakeBody = new SnakeBody(BLOCKSIZE, "Body");
                    snakeBody.setTranslateX(tailX);
                    snakeBody.setTranslateY(tailY);

                    snake.add(snakeBody);
                    currPlayer.raiseScore();
                    HighScore.checkScore(currPlayer.getScore(), currPlayer.getName());
                } else {
                    updateBanner(0);
                }

            }

            if (tail.getTranslateX() == foodToo.getPosX() && tail.getTranslateY() == foodToo.getPosY()){
                if(foodToo.getState().equals("Good")){
                    foodToo.reposition();
                    SnakeBody snakeBody = new SnakeBody(BLOCKSIZE, "Body");
                    snakeBody.setTranslateX(tailX);
                    snakeBody.setTranslateY(tailY);

                    snake.add(snakeBody);
                    currPlayer.raiseScore();
                    HighScore.checkScore(currPlayer.getScore(), currPlayer.getName());
                } else {
                    updateBanner(0);
                }
            }

        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(statementsBanner,score , gameInformation, food, foodToo, fullSnake, changeDifficultyButton);
        return root;
    }

    public void updateBanner(int type){
        pauseGame();
        String text = "";
        if (type == 0){
            text = "You ate rotten food.\nSnake got food poisoning and died.\nPress R to restart.";
        } else if (type == 1){
            text = "You ate yourself. \nSnake is vegetarian!\n Press R to restart.";
        } else if (type == 2){
            text = "You touched the boarder! \nSnake hit his head and died!\nPress R to restart.";
        } else {
            text = "You paused the game! \nPress P to play!";
        }
        statementsBanner.setText(text);
        statementsBanner.setVisible(true);
    }

    public void restartGame(){
        stopGame();
        startGame();
        currPlayer.resetScore();
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
        Scene scene = new Scene(createContent(((int) screenBoundHeight / BLOCKSIZE) - 3, ((int) screenBoundHeight / BLOCKSIZE) - 3));


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
                        updateBanner(3);
                        break;
                }
            }
            moved = false;
            if(!running){
                if(event.getCode() == KeyCode.P){
                    resumeGame();
                    statementsBanner.setVisible(false);
                } else if (event.getCode() == KeyCode.R){
                    restartGame();
                    statementsBanner.setVisible(false);
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
