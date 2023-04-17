package edu.hitsz.scoreDAO;

import java.util.List;

public interface ScoreDAO {
    /**
     * TODO
     * @param rank
     */
    void deleteByRank(int rank);
    void addScoreInfo(ScoreStructure info);
    void sortByScores();
    void printByScores();
    void saveScoreList();
    void deleteByTime(String time);
    List<ScoreStructure> getScoreInfos();




}
