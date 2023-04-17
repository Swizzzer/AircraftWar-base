package edu.hitsz.scoreDAO;

public class ScoreStructure implements Comparable<ScoreStructure>{
    private int score;
    private String userName;
    private String playTime;
    public ScoreStructure(){

    }

    public ScoreStructure(int score, String userName, String playTime){
        this.score = score;
        this.userName = userName;
        this.playTime = playTime;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public String getPlayTime() {
        return playTime;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public int compareTo(ScoreStructure s) {
        return s.getScore()-this.score;
    }


}
