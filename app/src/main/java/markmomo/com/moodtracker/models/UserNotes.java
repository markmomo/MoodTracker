package markmomo.com.moodtracker.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by markm On 20/03/2019.
 */
public class UserNotes {

    private Context mContext;

    public static final String NOTES = "NOTES";
    public static final String CURRENT_NOTES = "CURRENT_NOTES";


    public UserNotes(Context context) {
        mContext = context;
    }

    public ArrayList<String> getNotes() {

        SharedPreferences prefs;
        prefs = mContext.getSharedPreferences("myPrefs", MODE_PRIVATE);
        ArrayList<String> mNotes;
        String moods = prefs.getString(NOTES, "no note");
        mNotes = new ArrayList<>(Arrays.asList(moods.split(",;,;;,;;")));

        return mNotes;
    }
}