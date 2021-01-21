package at.ac.fhcampuswien;

import java.io.*;
import java.util.*;

public class HighScoreList {
    private final List<String> highscoreList;
    private static final File SCOREFILETABLE = new File("src/main/resources/highscoreTable.txt");

    public HighScoreList(){
        this.highscoreList = readFromFile();
    }

    private static List<String> readFromFile(){
        List<String> returnList = new LinkedList<>();
        if (!SCOREFILETABLE.exists()) {
            try {
                SCOREFILETABLE.createNewFile();
                Writer writer = new FileWriter(SCOREFILETABLE);
                writer.write("none:0\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("It's not a bug, its a feature!");
            }
        }
        try{
            Scanner scanner = new Scanner(SCOREFILETABLE);
            int i = 0;
            while(scanner.hasNext()){
                returnList.add(i, scanner.nextLine());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public static void checkRecordTable(String playername, int score, List<String> highscoreList){
        for (int i = 0; i < highscoreList.toArray().length; i++) {
            String[] scorefromList = highscoreList.get(i).split(":");
            if (Integer.parseInt(scorefromList[1].replace("\n","")) < score){
                highscoreList.add(i,  playername + ":" + score);
                break;
            }
        }
        updateFile(highscoreList);
    }

    public static void updateFile(List<String> highscoreList){
        SCOREFILETABLE.delete();
        File newSCOREFILETABLE = new File("src/main/resources/highscoreTable.txt");
        if (!SCOREFILETABLE.exists()) {
            try {
                SCOREFILETABLE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("It's not a bug, its a feature!");
            }
        }
        try{
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < highscoreList.toArray().length; i++) {
                String[] info = highscoreList.get(i).split(":");
                stringBuilder.append(info[0]).append(":").append(info[1]).append("\n");
            }

            FileWriter writer = new FileWriter(newSCOREFILETABLE, false);
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public List<String> getHighscoreList(){
        return this.highscoreList;
    }

    public List<String> getRecordTable(List<String> highscoreList) {
        if(highscoreList.toArray().length < 5){
            for (int i = highscoreList.toArray().length; i < 5; i++) {
                highscoreList.add(i, "none:0");
            }
        }
        return highscoreList;
    }
}
