package edu.hitsz.scoreDAO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import edu.hitsz.scoreDAO.*;

/**
 * This class implements the ScoreDAO interface and provides methods to add,
 * delete and print scores.
 */

public class ScoreStream implements ScoreDAO {
    private final List<ScoreStructure> scoreInfos = new LinkedList<>();
    public LinkedList<StringBuilder> data = new LinkedList<>();


    private String fileName;

    public ScoreStream(String fileName) {
        this.fileName = fileName;
    }

    // List to store the score information

    @Override
    public void addScoreInfo(ScoreStructure info) {
        // Get the existing scores from the file
        if (scoreInfos.isEmpty()) {
            getScoreList();
        }
        // Add the new score to the list
        scoreInfos.add(info);
        // Get the current time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String nowtime = formatter.format(date);
        try {
            // Append the new score to the file
            FileOutputStream out = new FileOutputStream(fileName, true);
            OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            writer.append(info.getUserName() + " " + nowtime + " " + (info.getScore()));
            writer.append("\r\n");
            writer.close();
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method reads the scores from the file and stores them in the list.
     */
    public void getScoreList() {
        File f = new File(fileName);
        FileInputStream in = null;
        try {
            in = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            // Create the file if it doesn't exist
            String filePath = "." + File.separator + fileName;
            File file = new File(filePath);
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                    in = new FileInputStream(f);
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e1) {
                System.out.println("An error occurred while creating the file.");
                e1.printStackTrace();
            }
        }
        InputStreamReader reader = null;
        reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        while (true) {
            String[] parts = null;
            try {
                if ((line = bufferedReader.readLine()) == null) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 按行读入文件,每行按空格分为6部分,分别对应用户名,时间和分数.

            parts = line.split(" ");
            if (parts.length != 6) {
                throw new IllegalArgumentException("Invalid input line: " + line);
            }
            ScoreStructure a = new ScoreStructure();
            a.setUserName(parts[0]);
            a.setPlayTime(parts[1] + " " + parts[2] + " " + parts[3] + " " + parts[4]);
            a.setScore(Integer.parseInt(parts[5]));
            scoreInfos.add(a);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByRank(int rank) {
        // Get the existing scores from the file
        if (scoreInfos.isEmpty()) {
            getScoreList();
        }
        // Check if the rank is valid
        if (rank <= 0 || rank > scoreInfos.size()) {
            throw new IllegalArgumentException("Invalid rank: " + rank);
        }
        // Remove the score with the given rank
        scoreInfos.remove(rank - 1);
        // Save the updated list to the file
        saveScoreList();
    }


    /**
     * This method saves the updated list of scores to the file.
     */
    public void saveScoreList() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            for (ScoreStructure info : scoreInfos) {
                writer.write(
                        info.getUserName() + " " + info.getPlayTime() + " " + info.getScore() + System.lineSeparator());
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Error saving score list to file", e);
        }
    }

    @Override
    public void sortByScores() {
        // Sort the list of scores
        Collections.sort(scoreInfos);
    }

    @Override
    public void printByScores() {
        // Sort the list of scores
        sortByScores();
        int i = 1;
        // Print the sorted list of scores
        for (ScoreStructure info : scoreInfos) {
            System.out.println("rank" + i + ":" + info.getUserName() + " " + info.getScore() + " " + info.getPlayTime());
            i++;
        }
    }

    public List<ScoreStructure> getScoreInfos() {
        if (scoreInfos.isEmpty()) {
            getScoreList();
        }
        sortByScores();
        return scoreInfos;
    }

    public void deleteByTime(String time) {
        if (scoreInfos.isEmpty()) {
            getScoreList();
        }
        // Remove the score with the given time
        boolean removed = scoreInfos.removeIf(info -> info.getPlayTime().equals(time));
        if (!removed) {
            throw new IllegalArgumentException("No score with time " + time + " found");
        }
        // Save the updated list to the file
        saveScoreList();
    }
}