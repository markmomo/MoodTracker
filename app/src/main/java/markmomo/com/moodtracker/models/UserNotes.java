package markmomo.com.moodtracker.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by markm On 20/03/2019.
 */
public class UserNotes {

    private Context mContext;
    private List<String> mNotes;

    private static final String NOTES = "NOTES";
    public static final String DAY_COUNTER_NOTES = "DAY_COUNTER_NOTES";
    public static final String CURRENT_NOTES = "CURRENT_NOTES";


    public UserNotes(Context context) {
        mContext = context;
    }

    public List<String> getNotes() {
        return mNotes;
    }

    private void updateNotesData(){
        SharedPreferences prefs = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        //initialize mNotes if empty
        if (mNotes == null || mNotes.isEmpty()) {
            mNotes = new ArrayList<>();
            mNotes.add(0,"no notes");
            while (mNotes.size() != 8){
                mNotes.add("no notes");
            }
        }

        //keep mMoods ArrayList size to 8 items
        while (mNotes.size() > 8){
            mNotes.remove(mNotes.size()-1);
        }
        // add empty entries on missing days and reset DAY_COUNTER_MOODS
        if (prefs.getInt(DAY_COUNTER_NOTES,0)>0){
            for(int i = 1;i < prefs.getInt(DAY_COUNTER_NOTES,0);i++){
                mNotes.add(0,"no note");
            }
            prefs.edit().putInt(DAY_COUNTER_NOTES,0).apply();
            mNotes.add(0,prefs.getString(CURRENT_NOTES,"no note"));

            //update today entry
        }else {
            mNotes.remove(0);
            mNotes.add(0,prefs.getString(CURRENT_NOTES,"no note"));
        }
    }

    private void saveNotesData() {
        SharedPreferences prefs = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        StringBuilder notesString = new StringBuilder();
        for (String s : mNotes){
            notesString.append(s);
            notesString.append(",;;,;;");
        }
        mNotes.clear();

        prefs.edit().putString(NOTES, notesString.toString()).apply();
    }

    private void loadNotesData() {
        SharedPreferences prefs = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String notes = prefs.getString(NOTES, "no notes");

        mNotes = new ArrayList<>(Arrays.asList(notes.split(",;;,;;")));
    }

    public void trackNotesData() {

        updateNotesData();
        saveNotesData();
        loadNotesData();
    }
}