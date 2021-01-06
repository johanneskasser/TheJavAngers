package at.ac.fhcampuswien;

import java.util.Scanner;

public class Player {
    private String name;
    private int highscore;

    public Player(String name) {
        this.name = name;
        this.highscore = 0;
    }

    public Player(){
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void createNewPlayer(){
        DialogWindow dialogWindow = new DialogWindow();
        dialogWindow.showLoginScreen();
    }

    public boolean checkPlayer(){
        boolean newPlayer = false;
        if (this.getName().equals("")){
            createNewPlayer();
            newPlayer = true;
        }
        return newPlayer;
    }
}
