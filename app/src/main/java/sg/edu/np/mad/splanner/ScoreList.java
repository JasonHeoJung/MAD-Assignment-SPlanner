package sg.edu.np.mad.splanner;

import java.util.ArrayList;

public class ScoreList {
    ArrayList<score> scoreList;

    public ScoreList(ArrayList<score> scoreList) {
        this.scoreList = scoreList;
    }

    public ArrayList<score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(score s) {
        this.scoreList.add(s);
    }
}
