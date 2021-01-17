package at.ac.fhcampuswien;

public class Player {
    private String name;
    private int score;
    double diffD;
    String difficultySting;

    public Player(){
        this.name = "none";
        this.score = 0;
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
        difficultySting = dialogWindow.getDifficultyString();
        this.diffD = switchString(difficultySting);
        if(!dialogWindow.getNameFromDialog().equals("none")){
            this.setName(dialogWindow.getNameFromDialog());
        }
    }

    public void updatePlayer(){
        DialogWindow dialogWindow = new DialogWindow();
        dialogWindow.showDiffChangeScreen();
        difficultySting = dialogWindow.getDifficultyString();
        this.diffD = switchString(difficultySting);
    }

    private double switchString(String in){
        double out = 2;
        switch (in) {
            case "Easy" -> out = 1;
            case "Expert" -> out = 3;
        }
        return out;
    }

    public double getDiffD(){
        return this.diffD;
    }

    public void checkPlayer(){
        if (this.getName().equals("none")){
            createNewPlayer();
        }
    }

    public String getDifficultySting() {
        return this.difficultySting;
    }

    public void raiseScore(){
        this.score += 100;
    }

    public int getScore(){
        return this.score;
    }

    public void resetScore(){
        this.score = 0;
    }
}
