package at.ac.fhcampuswien;

import java.io.*;

public class HighScore {
    private static final File scoreFile = new File("src/main/resources/highscore_save.txt");

    public static String[] get() {
        //Gets highscore from file
        //String format: Player_name:1500
        FileReader readFile;
        BufferedReader reader;
        String result = "0";
        if (!scoreFile.exists()) {
            //Creates new file if it does not exist (Error handling).
            try {
                scoreFile.createNewFile();
                Writer writer = new FileWriter(scoreFile);
                writer.write("none:0");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("It's not a bug, its a feature!");
            }
        }
        try {
            readFile = new FileReader(scoreFile);
            reader = new BufferedReader(readFile);
            result = reader.readLine();
            reader.close();
            readFile.close();
        } catch (IOException e) {
            System.out.println("It's not a bug, its a feature!");
        }
        return result.split(":");
    }

    public static void checkScore(int newScore, String playerName) {
        //Checks if new highscore is set
        //If new highscore is set, it stores new name and new score to file.
        //String format: Player_name:1500
        String[] information = get();
        int currentHighScore = Integer.parseInt(information[1]);
        if (newScore > currentHighScore) {
            try{
                Writer wr = new FileWriter(scoreFile);
                String output = playerName + ":" + newScore;
                wr.write(output);
                wr.close();
            } catch (IOException i){
                i.printStackTrace();
                System.out.println("It's not a bug, its a feature!");
            }
        }
    }
}
