package at.ac.fhcampuswien;

import java.io.*;

public class HighScore {
    private static File scoreFile = new File("build/tmp/highscore_save.txt");

    public static String get() {
        FileReader readFile;
        BufferedReader reader;
        String result = "0";
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            readFile = new FileReader(scoreFile);
            reader = new BufferedReader(readFile);
            result = reader.readLine();
            reader.close();
            readFile.close();
        } catch (IOException e) {
            return result;
        }
        return result;
    }

    public static void checkScore(int newScore) {
        if (newScore > Integer.parseInt(get())) {
            try{
                Writer wr = new FileWriter(scoreFile);
                wr.write(Integer.toString(newScore));
                wr.close();
            } catch (IOException i){
                i.printStackTrace();
                System.out.println("It's not a bug, its a feature!");
            }
        }
    }
}
