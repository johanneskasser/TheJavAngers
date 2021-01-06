package at.ac.fhcampuswien;

public class Player {
    private String name;
    private int highscore;

    public Player(String name) {
        this.name = name;
        this.highscore = 0;
    }

    public Player(){
        this.name = "none";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void createNewPlayer(){
        DialogWindow dialogWindow = new DialogWindow();
        dialogWindow.showLoginScreen();
        if(!dialogWindow.getNameFromDialog().equals("none")){
            this.setName(dialogWindow.getNameFromDialog());
        }
    }

    public void checkPlayer(){
        if (this.getName().equals("none")){
            createNewPlayer();
        }
    }
}
