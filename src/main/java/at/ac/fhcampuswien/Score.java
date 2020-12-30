package at.ac.fhcampuswien;

public class Score {
    private int scoreINT;

    public Score() {
        this.scoreINT = 0;
    }

   public void raiseScore(){
        scoreINT += 100;
   }

    public int getScoreINT() {
        return scoreINT;
    }

    public void resetScore(){
        scoreINT = 0;
    }
}
