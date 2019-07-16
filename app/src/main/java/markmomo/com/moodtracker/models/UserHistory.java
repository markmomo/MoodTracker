package markmomo.com.moodtracker.models;

import java.util.ArrayList;

/**
 * Created by markm On 13/07/2019.
 */
public class UserHistory {

    private int mCurrentMood;
    private String mCurrentComment;
    private ArrayList<Integer> moodsHistory;
    private ArrayList<String> commentsHistory;

    public int getCurrentMood() {
        return mCurrentMood;
    }

    public String getCurrentComment() {
        return mCurrentComment;
    }

    public ArrayList<Integer> getMoodsHistory() {
        return moodsHistory;
    }

    public ArrayList<String> getCommentHistory() {
        return commentsHistory;
    }

    public void setCurrentMood(int currentMood) {
        mCurrentMood = currentMood;
    }

    public void setCurrentComment(String currentComment) {
        mCurrentComment = currentComment;
    }

    public void setMoodsHistory(ArrayList<Integer> moodsHistory) {
        this.moodsHistory = moodsHistory;
    }

    public void setCommentHistory(ArrayList<String> commentsHistory) {
        this.commentsHistory = commentsHistory;
    }
}