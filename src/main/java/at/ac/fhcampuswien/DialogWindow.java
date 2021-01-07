package at.ac.fhcampuswien;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DialogWindow {
    String name = "none";

    public void showLoginScreen(){
        Stage stage = new Stage();

        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);

        Label label = new Label("Please Enter your name here!");
        TextField textName = new TextField();

        Button enter = new Button("Enter");

        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                name = textName.getText();
            }
        });
        box.getChildren().addAll(label, textName, enter);
        Scene scene = new Scene(box, 250, 150);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.showAndWait();
    }

    public String getNameFromDialog() {
        return name;
    }
}
