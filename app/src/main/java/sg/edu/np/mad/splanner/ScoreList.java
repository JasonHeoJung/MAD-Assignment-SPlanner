package sg.edu.np.mad.splanner;

import java.util.ArrayList;

public class ScoreList {
    ArrayList<Score> scoreList;

    public ScoreList(ArrayList<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public ArrayList<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(Score s) {
        this.scoreList.add(s);
    }
}
