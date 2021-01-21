package at.ac.fhcampuswien;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class DialogWindow {
    String name = "none";
    String difficultyString = "Medium";

    public void showLoginScreen(){
        Stage stage = new Stage();

        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);

        Label label = new Label("Please Enter your name here!");
        TextField textName = new TextField();

        MenuButton difficulty = new MenuButton(" Please select game difficulty!");
        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem expert = new MenuItem("Expert");
        difficulty.getItems().addAll(easy, medium, expert);
        Label diff = new Label("No difficulty selected!");
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                diff.setText(((MenuItem)event.getSource()).getText() + " selected!");
                difficultyString = ((MenuItem)event.getSource()).getText();
            }
        };

        easy.setOnAction(eventHandler);
        medium.setOnAction(eventHandler);
        expert.setOnAction(eventHandler);

        Button enter = new Button("Start Game!");

        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                name = textName.getText();
            }
        });
        box.getChildren().addAll(label, textName, difficulty, diff, enter);
        Scene scene = new Scene(box, 250, 150);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.showAndWait();
    }

    public void showDiffChangeScreen(){
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label("Change Difficulty!");
        MenuButton difficulty = new MenuButton(" Please select game difficulty!");
        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem expert = new MenuItem("Expert");
        difficulty.getItems().addAll(easy, medium, expert);
        Label diff = new Label("No difficulty selected!");
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                diff.setText(((MenuItem)event.getSource()).getText() + " selected!");
                difficultyString = ((MenuItem)event.getSource()).getText();
            }
        };

        easy.setOnAction(eventHandler);
        medium.setOnAction(eventHandler);
        expert.setOnAction(eventHandler);

        Button enter = new Button("Start Game!");

        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        vbox.getChildren().addAll(label, difficulty, diff, enter);
        Scene scene = new Scene(vbox, 250, 150);
        stage.setScene(scene);
        stage.setTitle("Difficulty Selection");
        stage.showAndWait();
    }


    public void showHighScoreTable(List<String> highscorelist){
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();

        HighScoreList highScoreList = new HighScoreList();
        List<String> highscoreInfo = highScoreList.getRecordTable(highscorelist);

        Label leaderboardLabel = new Label("Leaderboard:");
        leaderboardLabel.setFont(Font.font("Monospaced", 30));
        leaderboardLabel.setTextFill(Color.DARKBLUE);

        StringBuilder stringBuilderName = new StringBuilder();
        StringBuilder stringBuilderScore = new StringBuilder();

        for (int i = 0; i <= 5 ; i++) {
            String[] info = highscoreInfo.get(i).split(":");
            stringBuilderName.append(info[0]).append("    \n");
            stringBuilderScore.append(info[1]).append("\n");
        }

        Label infoName = new Label(stringBuilderName.toString());
        infoName.setTextFill(Color.BLACK);
        infoName.setFont(Font.font("Monospaced", 20));

        Label infoScore = new Label(stringBuilderScore.toString());
        infoScore.setTextFill(Color.BLACK);
        infoScore.setFont(Font.font("Monospaced", 20));

        vbox.getChildren().addAll(leaderboardLabel);
        hBox.getChildren().addAll(infoName, infoScore);

        borderPane.setTop(vbox);
        borderPane.setCenter(hBox);

        Scene scene = new Scene(borderPane, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Leaderboard");
        stage.show();
    }



    public String getNameFromDialog() {
        return name;
    }

    public String getDifficultyString() {
        return difficultyString;
    }
}
