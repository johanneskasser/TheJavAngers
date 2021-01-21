package at.ac.fhcampuswien;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Banner extends Text {

    public Banner(){
        //Set Layout for the banner.
        super();
        this.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        this.setFill(Color.RED);
        this.setTextAlignment(TextAlignment.CENTER);
        this.setVisible(false);
    }

}
